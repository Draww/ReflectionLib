package us.nullbytes.basic.reflection.annotations;


import us.nullbytes.basic.reflection.ResolvePolicy;
import us.nullbytes.basic.reflection.resolver.ClassResolver;
import us.nullbytes.basic.reflection.resolver.ConstructorResolver;
import us.nullbytes.basic.reflection.resolver.FieldResolver;
import us.nullbytes.basic.reflection.resolver.MethodResolver;
import us.nullbytes.basic.reflection.utility.AccessUtility;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public final class ExpressedSuplement {

	public static void inject(Object instance) {
		Field[] declaredFields = instance.getClass().getDeclaredFields();
		for (Field f : declaredFields) {
			if (f.getAnnotations().length > 0) {
				try {
					injectField(instance, f);
				} catch (ReflectiveOperationException e) {
				}
			}
		}
	}

	public static void inject(Class<?> clazz) {
		Field[] declaredFields = clazz.getDeclaredFields();
		for (Field f : declaredFields) {
			if (java.lang.reflect.Modifier.isStatic(f.getModifiers())) {
				if (f.getAnnotations().length > 0) {
					try {
						injectField(null, f);
					} catch (ReflectiveOperationException e) {
					}
				}
			}
		}
	}

	public static void injectField(Object item, Field field) throws ReflectiveOperationException {
		field = AccessUtility.allowAccess(field);
		ExpressedClass eclass;
		ExpressedConstructor econst;
		ExpressedMethod emethod;
		ExpressedField efield;
		if ((eclass = field.getAnnotation(ExpressedClass.class)) != null) {
			apply(item, field, eclass);
		} else if ((econst = field.getAnnotation(ExpressedConstructor.class)) != null) {
			apply(item, field, econst);
		} else if ((emethod = field.getAnnotation(ExpressedMethod.class)) != null) {
			apply(item, field, emethod);
		} else if ((efield = field.getAnnotation(ExpressedField.class)) != null) {
			apply(item, field, efield);
		}
	}

	public static void apply(Object item, Field field, ExpressedClass expressed) throws ReflectiveOperationException {
		Class<?> clazz = new ClassResolver().use(expressed.name()).resolveWithPolicy(expressed.resolver());
		field.set(item, clazz);
	}

	public static void apply(Object item, Field field, ExpressedConstructor expressed) throws ReflectiveOperationException {
		Class<?> clazz = new ClassResolver().use(expressed.clazz()).resolveWithPolicy(expressed.resolver());
		if (clazz == null) {
			field.set(item, null);
			return;
		}
		ConstructorResolver resolver = new ConstructorResolver(clazz);
		Constructor<?> output = null;
		switch (expressed.policy()) {
			case FIRST:
			case LAST:
			case NORMAL:
				output = resolver.use(expressed.parameters()).resolveWithPolicy(expressed.resolver());
				break;
			case INDEX:
				if (expressed.index() != -1) {
					output = resolver.resolveIndexWithPolicy(expressed.index(), expressed.resolver());
				}
				break;
		}
		field.set(item, output);
	}

	public static void apply(Object item, Field field, ExpressedMethod expressed) throws ReflectiveOperationException {
		Class<?> clazz = new ClassResolver().use(expressed.clazz()).resolveWithPolicy(expressed.resolver());
		if (clazz == null) {
			field.set(item, null);
			return;
		}
		MethodResolver resolver = new MethodResolver(clazz);
		Method output = null;
		switch (expressed.policy()) {
			case NORMAL:
				if (!expressed.name().isEmpty()) {
					output = resolver.use(expressed.name(), expressed.parameters()).resolveWithPolicy(expressed.resolver());
					break;
				}
			case FIRST:
				if (expressed.name().isEmpty()) {
					output = resolver.resolveFirstWithPolicy(expressed.parameters(), expressed.resolver());
				} else {
					output = resolver.resolveFirstNameWithPolicy(expressed.name(), expressed.resolver());
				}
				break;
			case LAST:
				if (expressed.name().isEmpty()) {
					output = resolver.resolveLastWithPolicy(expressed.parameters(), expressed.resolver());
				} else {
					output = resolver.resolveLastNameWithPolicy(expressed.name(), expressed.resolver());
				}
				break;
			case INDEX:
				if (expressed.index() != -1) {
					output = resolver.resolveIndexWithPolicy(expressed.index(), expressed.resolver());
				}
				break;
		}
		field.set(item, output);
	}

	public static void apply(Object item, Field field, ExpressedField expressed) throws ReflectiveOperationException {
		Class<?> clazz = new ClassResolver().use(expressed.clazz()).resolveWithPolicy(expressed.resolver());
		if (clazz == null) {
			field.set(item, null);
			return;
		}
		FieldResolver resolver = new FieldResolver(clazz);
		Field output = null;
		switch (expressed.policy()) {
			case NORMAL:
				if (!expressed.name().isEmpty()) {
					output = resolver.use(expressed.name(), expressed.type()).resolveWithPolicy(expressed.resolver());
					break;
				}
			case FIRST:
				if (expressed.name().isEmpty()) {
					output = resolver.resolveFirstWithPolicy(expressed.type(), expressed.resolver());
				} else {
					output = resolver.resolveFirstNameWithPolicy(expressed.name(), expressed.resolver());
				}
				break;
			case LAST:
				if (expressed.name().isEmpty()) {
					output = resolver.resolveLastWithPolicy(expressed.type(), expressed.resolver());
				} else {
					output = resolver.resolveLastNameWithPolicy(expressed.name(), expressed.resolver());
				}
				break;
			case INDEX:
				if (expressed.index() != -1) {
					output = resolver.resolveIndexWithPolicy(expressed.index(), expressed.resolver());
				}
				break;
		}
		field.set(item, output);
	}

}
