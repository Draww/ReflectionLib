package us.nullbytes.basic.reflection.annotations;

import us.nullbytes.basic.reflection.ResolvePolicy;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ExpressedClass {

	String name();

	ResolvePolicy resolver() default ResolvePolicy.QUIET;

}
