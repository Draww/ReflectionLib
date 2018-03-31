package us.nullbytes.basic.reflection;

import us.nullbytes.basic.reflection.exception.FailedExecutionException;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public abstract class AbstractResolver<K, V> {

	private final Set<V> arguments = new HashSet<>();

	public K resolve() {
		return resolveWithPolicy(ResolvePolicy.VERBOSE);
	}

	public K resolveWithPolicy(ResolvePolicy policy) {
		Iterator<V> argumentIterator = arguments.iterator();
		for (V next = argumentIterator.next(); argumentIterator.hasNext(); ) {
			K result = resolve(next);
			if (result != null) {
				return result;
			}
		}
		throwException(policy, "Failed to resolve with " + toString());
		return null;
	}

	public abstract K resolve(V argument);

	public AbstractResolver<K, V> use(V argument) {
		arguments.add(argument);
		return this;
	}

	public Set<V> getArguments() {
		return arguments;
	}

	public void throwException(ResolvePolicy policy, String string) {
		switch (policy) {
			case SILENT:
				return;
			case VERBOSE:
				throw new FailedExecutionException(string);
			case QUIET:
				System.out.println(string);
				return;
		}
	}

	@Override
	public abstract String toString();

}
