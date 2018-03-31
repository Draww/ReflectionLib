package us.nullbytes.basic.reflection.utility;

import javafx.util.Pair;

/**
 * @author NullByte
 * <p>
 * This class gives a method 'of' to easily create Pair sets
 */
public final class Combo {

	/**
	 * Method used to create a Pair of values
	 *
	 * @param ok  object of K generic
	 * @param ov  object of V generic
	 * @param <K> key generic
	 * @param <V> value generic
	 * @return the constructed pair with ok and ov
	 */
	public static <K, V> Pair<K, V> of(K ok, V ov) {
		return new Pair<>(ok, ov);
	}

}
