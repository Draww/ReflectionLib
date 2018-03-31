package us.nullbytes.basic.reflection;

import javafx.util.Pair;
import us.nullbytes.basic.reflection.utility.Combo;

import java.lang.reflect.Field;

public abstract class TypeResolver<X, Y, Z> extends MemberResolver<X, Y> {

	public TypeResolver(String clazz) throws ReflectiveOperationException {
		super(clazz);
	}

	public TypeResolver(Class<?> clazz) {
		super(clazz);
	}

	public X resolveFirst(Z argument) {
		return resolveFirstWithPolicy(argument, ResolvePolicy.VERBOSE);
	}

	public X resolveFirstWithPolicy(Z argument, ResolvePolicy policy) {
		X result = resolveLocalFirst(argument);
		if (result == null) {
			throwException(policy, "Failed to load argument `" + String.valueOf(argument) + "` in class " + getClazz().toString());
			return null;
		}
		return result;
	}

	public abstract X resolveLocalFirst(Z argument);

	public X resolveLast(Z argument) {
		return resolveLastWithPolicy(argument, ResolvePolicy.VERBOSE);
	}

	public X resolveLastWithPolicy(Z argument, ResolvePolicy policy) {
		X result = resolveLocalLast(argument);
		if (result == null) {
			throwException(policy, "Failed to load argument `" + String.valueOf(argument) + "` in class " + getClazz().toString());
			return null;
		}
		return result;
	}

	public abstract X resolveLocalLast(Z argument);

	public X resolveFirstName(String name) {
		return resolveFirstNameWithPolicy(name, ResolvePolicy.VERBOSE);
	}

	public X resolveFirstNameWithPolicy(String name, ResolvePolicy policy) {
		X result = resolveLocalFirstName(name);
		if (result == null) {
			throwException(policy, "Failed to load type `" + name + "` in class " + getClazz().toString());
			return null;
		}
		return result;
	}

	public abstract X resolveLocalFirstName(String name);

	public X resolveLastName(String name) {
		return resolveLastNameWithPolicy(name, ResolvePolicy.VERBOSE);
	}

	public X resolveLastNameWithPolicy(String name, ResolvePolicy policy) {
		X result = resolveLocalLastName(name);
		if (result == null) {
			throwException(policy, "Failed to load type `" + name + "` in class " + getClazz().toString());
			return null;
		}
		return result;
	}

	public abstract X resolveLocalLastName(String name);

}
