package net.ci010.minecrafthelper.util;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author ci010
 */
public class GenericUtil
{
	public static Type getGenericType(Object obj, int parameter)
	{
		return ((ParameterizedType) obj.getClass()
				.getGenericSuperclass())
				.getActualTypeArguments()
				[parameter];
	}

	public static Type getGenericType(Object obj)
	{
		return getGenericType(obj, 0);
	}

	public static <T> Class<T> getGenericTypeTo(Object obj, int parameter)
	{
		return (Class<T>) getGenericType(obj, parameter);
	}

	public static <T> Class<T> getGenericTypeTo(Object obj)
	{
		return getGenericTypeTo(obj, 0);
	}

	public static <T> Class<T> cast(Class<?> clz)
	{
		Class<T> future = null;
		try
		{
			future = (Class<T>) clz;
		}
		catch (Exception e)
		{//TODO check
			e.printStackTrace();
		}
		if (future != null)
			return future;
		else throw new ClassCastException();
	}
}

