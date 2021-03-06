package examples;

import us.nullbytes.basic.reflection.ResolvePolicy;
import us.nullbytes.basic.reflection.annotations.*;
import us.nullbytes.basic.reflection.resolver.ClassResolver;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class FooBar {

	private String basic;

	public FooBar() {
		this.basic = "FooBar";
	}

	public FooBar(String string) {
		this.basic = string;
	}

	public static void main(String[] args) throws ReflectiveOperationException {
		ExpressedSuplement.inject(Foo.class);
		Foo.bar();
		Bar bar = new Bar();
		ExpressedSuplement.inject(bar);
		bar.foo();
	}

	public void print(String string) {
		System.out.println(string);
	}

	static class Foo {

		@ExpressedConstructor(clazz = "examples.FooBar")
		private static Constructor<?> constructor;
		@ExpressedMethod(clazz = "examples.FooBar", name = "print", policy = MemberPolicy.FIRST)
		private static Method method;
		@ExpressedField(clazz = "examples.FooBar", name = "basic", policy = MemberPolicy.FIRST)
		private static Field field;

		public static void bar() throws ReflectiveOperationException {
			Object instance = constructor.newInstance();
			method.invoke(instance, field.get(instance));
		}

	}

	static class Bar {

		@ExpressedConstructor(clazz = "examples.FooBar", parameters = {String.class})
		private Constructor<?> constructor;
		@ExpressedMethod(clazz = "examples.FooBar", parameters = {String.class}, policy = MemberPolicy.FIRST)
		private Method method;
		@ExpressedField(clazz = "examples.FooBar", type = String.class, policy = MemberPolicy.FIRST)
		private Field field;

		public void foo() throws ReflectiveOperationException {
			Object instance = constructor.newInstance("BarFoo");
			method.invoke(instance, field.get(instance));
		}

	}

}
