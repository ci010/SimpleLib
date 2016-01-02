package net.simplelib.registry.annotation.field;

import net.simplelib.registry.abstracts.ArgumentHelper;
import net.simplelib.registry.annotation.ConstructOption;

import java.lang.annotation.*;

/**
 * The field annotated by this
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
	@interface Ignore
	{
	}

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
