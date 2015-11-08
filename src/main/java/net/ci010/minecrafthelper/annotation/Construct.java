package net.ci010.minecrafthelper.annotation;

import net.ci010.minecrafthelper.abstracts.ArgumentHelper;

import java.lang.annotation.*;

/**
 * The field
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value =
		{ElementType.FIELD})
public @interface Construct
{
	Class<?> value();

	boolean register() default true;

	@Retention(RetentionPolicy.RUNTIME)
	@Target(value =
			{ElementType.FIELD})
	@ConstructOption(FloatHelper.class)
	@interface Float
	{
		float value();
	}

	class FloatHelper implements ArgumentHelper
	{
		@Override
		public Object[] getArguments(Annotation annotation)
		{
			return new Object[]
					{((Float) annotation).value()};
		}
	}
}
