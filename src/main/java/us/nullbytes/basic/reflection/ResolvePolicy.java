package us.nullbytes.basic.reflection;

/**
 * @author NullByte
 * <p>
 * Policy to show how an exception should be handled.
 */
public enum ResolvePolicy {

	/**
	 * Shows fully thrown exception
	 */
	VERBOSE,
	/**
	 * Shows only a message to note what happened.
	 */
	QUIET,
	/**
	 * Do not do anything
	 */
	SILENT

}
