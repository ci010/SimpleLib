package net.ci010.minecrafthelper.annotation;

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
public @interface ModEntity
{
	String name() default "";

	int id() default -1;

	int trackingRange() default 32;

	int updateFrequency() default 3;

	boolean sendsVelocityUpdates() default true;
}
