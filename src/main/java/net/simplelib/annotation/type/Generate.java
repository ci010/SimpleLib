package net.simplelib.annotation.type;

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
public @interface Generate
{
	enum GenerateType
	{
		model, language
	}

	GenerateType[] value();

	String[] supportLang() default {""};
}
