package us.nullbytes.basic.reflection;

public abstract class MemberResolver<K, V> extends AbstractResolver<K, V> {

	private final Class<?> clazz;

	public MemberResolver(String clazz) throws ReflectiveOperationException {
		this(Class.forName(clazz));
	}

	public MemberResolver(Class<?> clazz) {
		this.clazz = clazz;
	}

	public K resolveIndex(int index) {
		return resolveIndexWithPolicy(index, ResolvePolicy.VERBOSE);
	}

	public K resolveIndexWithPolicy(int index, ResolvePolicy policy) {
		K result = resolveLocalIndex(index);
		if (result == null) {
			throwException(policy, "Failed to load index `" + index + "` in class " + getClazz().toString());
			return null;
		}
		return result;
	}

	public abstract K resolveLocalIndex(int index);

	public Class<?> getClazz() {
		return clazz;
	}
}
