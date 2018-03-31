package us.nullbytes.basic.reflection.utility;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author NullByte
 * <p>
 * Gives ability to allow access to all types of reflective objects
 */
public final class AccessUtility {

	/**
	 * Allows access to a specified field
	 *
	 * @param field to allow access to
	 * @return the field instance
	 */
	public static Field allowAccess(Field field) {
		try {
			field.setAccessible(true);
			Field modifiersField = Field.class.getDeclaredField("modifiers");
			modifiersField.setAccessible(true);
			modifiersField.setInt(field, field.getModifiers() & 0xFFFFFFEF);
			return field;
		} catch (ReflectiveOperationException ex) {
			field.setAccessible(true);
			return field;
		}
	}

	/**
	 * Allows access to a specified method
	 *
	 * @param method to allow access to
	 * @return the method instance
	 */
	public static Method allowAccess(Method method) {
		method.setAccessible(true);
		return method;
	}

	/**
	 * Allows access to a specified constructor
	 *
	 * @param constructor to allow access to
	 * @return the constructor instance
	 */
	public static Constructor<?> allowAccess(Constructor<?> constructor) {
		constructor.setAccessible(true);
		return constructor;
	}

}
