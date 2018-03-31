package us.nullbytes.basic.reflection.resolver;

import javafx.util.Pair;
import us.nullbytes.basic.reflection.AbstractResolver;
import us.nullbytes.basic.reflection.TypeResolver;
import us.nullbytes.basic.reflection.utility.AccessUtility;
import us.nullbytes.basic.reflection.utility.Combo;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class MethodResolver extends TypeResolver<Method, Pair<String, Class<?>[]>, Class<?>[]> {

	public MethodResolver(String clazz) throws ReflectiveOperationException {
		super(clazz);
	}

	public MethodResolver(Class<?> clazz) {
		super(clazz);
	}

	@Override
	public Method resolveLocalFirst(Class<?>[] argument) {
		for (Method method : getClazz().getDeclaredMethods()) {
			if (doesClassesEquate(argument, method.getParameterTypes())) {
				return AccessUtility.allowAccess(method);
			}
		}
		return null;
	}

	@Override
	public Method resolveLocalLast(Class<?>[] argument) {
		Method next = null;
		for (Method method : getClazz().getDeclaredMethods()) {
			if (doesClassesEquate(argument, method.getParameterTypes())) {
				next = method;
			}
		}
		return next == null ? null : AccessUtility.allowAccess(next);
	}

	@Override
	public Method resolveLocalFirstName(String name) {
		for (Method method : getClazz().getDeclaredMethods()) {
			if (method.getName().equals(name)) {
				return AccessUtility.allowAccess(method);
			}
		}
		return null;
	}

	@Override
	public Method resolveLocalLastName(String name) {
		Method next = null;
		for (Method method : getClazz().getDeclaredMethods()) {
			if (method.getName().equals(name)) {
				next = method;
			}
		}
		return next == null ? null : AccessUtility.allowAccess(next);
	}

	@Override
	public Method resolveLocalIndex(int index) {
		Method[] declared = getClazz().getDeclaredMethods();
		if (declared.length > index) {
			return AccessUtility.allowAccess(declared[index]);
		}
		return null;
	}

	@Override
	public Method resolve(Pair<String, Class<?>[]> argument) {
		for (Method method : getClazz().getDeclaredMethods()) {
			if (method.getName().equals(argument.getKey())) {
				if (doesClassesEquate(argument.getValue(), method.getParameterTypes())) {
					return AccessUtility.allowAccess(method);
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
		return "MethodResolver={" + builder.toString() + '}';
	}

	private boolean doesClassesEquate(Class<?>[] o1, Class<?>[] o2) {
		boolean equal = true;
		if (o1.length != o2.length) {
			return false;
		}
		for (int i = 0; i < o1.length; i++) {
			if (o1[i] != o2[i] && !o1[i].equals(o2[i])) {
				equal = false;
				break;
			}
		}
		return equal;
	}

	public AbstractResolver<Method, Pair<String, Class<?>[]>> use(String string, Class<?>... args) {
		return super.use(Combo.of(string, args));
	}

}
