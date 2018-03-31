package us.nullbytes.basic.reflection.resolver;

import javafx.util.Pair;
import us.nullbytes.basic.reflection.AbstractResolver;
import us.nullbytes.basic.reflection.TypeResolver;
import us.nullbytes.basic.reflection.utility.AccessUtility;
import us.nullbytes.basic.reflection.utility.Combo;

import java.lang.reflect.Field;

public class FieldResolver extends TypeResolver<Field, Pair<String, Class<?>[]>, Class<?>> {

	public FieldResolver(String clazz) throws ReflectiveOperationException {
		super(clazz);
	}

	public FieldResolver(Class<?> clazz) {
		super(clazz);
	}

	@Override
	public Field resolveLocalFirst(Class<?> argument) {
		for (Field field : getClazz().getDeclaredFields()) {
			if (field.getType().equals(argument)) {
				return AccessUtility.allowAccess(field);
			}
		}
		return null;
	}

	@Override
	public Field resolveLocalLast(Class<?> argument) {
		Field next = null;
		for (Field field : getClazz().getDeclaredFields()) {
			if (field.getType().equals(argument)) {
				next = field;
			}
		}
		return next == null ? null : AccessUtility.allowAccess(next);
	}

	@Override
	public Field resolveLocalFirstName(String name) {
		for (Field field : getClazz().getDeclaredFields()) {
			if (field.getName().equals(name)) {
				return AccessUtility.allowAccess(field);
			}
		}
		return null;
	}

	@Override
	public Field resolveLocalLastName(String name) {
		Field next = null;
		for (Field field : getClazz().getDeclaredFields()) {
			if (field.getName().equals(name)) {
				next = field;
			}
		}
		return next == null ? null : AccessUtility.allowAccess(next);
	}

	@Override
	public Field resolveLocalIndex(int index) {
		Field[] declared = getClazz().getDeclaredFields();
		if (declared.length > index) {
			return AccessUtility.allowAccess(declared[index]);
		}
		return null;
	}

	@Override
	public Field resolve(Pair<String, Class<?>[]> argument) {
		for (Field field : getClazz().getDeclaredFields()) {
			if (field.getName().equals(argument.getKey())) {
				for (Class<?> clazz : argument.getValue()) {
					if (field.getType().equals(clazz)) {
						return AccessUtility.allowAccess(field);
					}
				}
			}
		}
		return null;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		for (Pair<String, Class<?>[]> classesArray : getArguments()) {
			builder.append(classesArray.getKey()).append('[');
			for (Class<?> clazz : classesArray.getValue()) {
				builder.append(clazz.toString()).append(',').append(' ');
			}
			if (classesArray.getValue().length > 0) {
				builder.deleteCharAt(builder.length() - 1);
				builder.deleteCharAt(builder.length() - 1);
			}
			builder.append(']').append(',').append(' ');
		}
		if (getArguments().size() > 0) {
			builder.deleteCharAt(builder.length() - 1);
			builder.deleteCharAt(builder.length() - 1);
		}
		return "FieldResolver={" + builder.toString() + '}';
	}

	public AbstractResolver<Field, Pair<String, Class<?>[]>> use(String string, Class<?>... args) {
		return super.use(Combo.of(string, args));
	}
}
