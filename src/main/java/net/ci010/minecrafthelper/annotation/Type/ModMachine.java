package net.ci010.minecrafthelper.annotation.type;

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
public @interface ModMachine
{
	/**
	 * @return The name of the Machine. If this is blank, it will be the class name.
	 */
	String value() default "";
}