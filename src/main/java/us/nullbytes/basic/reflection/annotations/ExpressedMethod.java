package us.nullbytes.basic.reflection.annotations;

import us.nullbytes.basic.reflection.ResolvePolicy;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ExpressedMethod {

	String clazz();

	String name() default "";

	Class<?>[] parameters() default {};

	int index() default -1;

	MemberPolicy policy() default MemberPolicy.NORMAL;

	ResolvePolicy resolver() default ResolvePolicy.QUIET;

}
