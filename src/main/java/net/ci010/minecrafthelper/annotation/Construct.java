package net.ci010.minecrafthelper.annotation;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import net.ci010.minecrafthelper.abstracts.ArgumentHelper;

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
