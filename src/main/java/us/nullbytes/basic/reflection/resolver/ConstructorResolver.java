package us.nullbytes.basic.reflection.resolver;

import us.nullbytes.basic.reflection.MemberResolver;
import us.nullbytes.basic.reflection.utility.AccessUtility;

import java.lang.reflect.Constructor;

public class ConstructorResolver extends MemberResolver<Constructor<?>, Class<?>[]> {

	public ConstructorResolver(String clazz) throws ReflectiveOperationException {
		super(clazz);
	}

	public ConstructorResolver(Class<?> clazz) {
		super(clazz);
	}

	@Override
	public Constructor<?> resolveLocalIndex(int index) {
		Class<?> clazz = getClazz();
		if (clazz.getDeclaredConstructors().length < index) {
			return null;
		}
		return AccessUtility.allowAccess(clazz.getDeclaredConstructors()[index]);
	}

	@Override
	public Constructor<?> resolve(Class<?>[] argument) {
		try {
			return AccessUtility.allowAccess(getClazz().getDeclaredConstructor(argument));
		} catch (NoSuchMethodException e) {
			return null;
		}
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		for (Class<?>[] classesArray : getArguments()) {
			builder.append('[');
			for (Class<?> clazz : classesArray) {
				builder.append(clazz.toString()).append(',').append(' ');
			}
			if (classesArray.length > 0) {
				builder.deleteCharAt(builder.length() - 1);
				builder.deleteCharAt(builder.length() - 1);
			}
			builder.append(']').append(',').append(' ');
		}
		if (getArguments().size() > 0) {
			builder.deleteCharAt(builder.length() - 1);
			builder.deleteCharAt(builder.length() - 1);
		}
		return "ConstructorResolver={" + builder.toString() + '}';
	}
}
