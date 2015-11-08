package net.ci010.minecrafthelper.annotation;

import net.ci010.minecrafthelper.abstracts.ArgumentHelper;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author ci010
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value =
		{ElementType.ANNOTATION_TYPE})
public @interface ConstructOption
{
	Class<? extends ArgumentHelper> value();
}
