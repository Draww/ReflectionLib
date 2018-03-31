package us.nullbytes.basic.reflection.resolver;

import us.nullbytes.basic.reflection.AbstractResolver;

public class ClassResolver extends AbstractResolver<Class<?>, String> {

	@Override
	public Class<?> resolve(String argument) {
		try {
			return Class.forName(argument);
		} catch (ReflectiveOperationException ex) {
			return null;
		}
	}

	@Override
	public String toString() {
		return "ClassResolver={" + getArguments().toString() + '}';
	}
}
