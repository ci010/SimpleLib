package net.simplelib.world;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author ci010
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value =
		{ElementType.TYPE})
public @interface IWorldProperties
{
	int dimension() default 0;

	String name() default "";
}
