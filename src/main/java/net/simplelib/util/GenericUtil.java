package net.simplelib.util;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * This class works for those who really don't want IDE warning in their code. Use this carefully please.
 *
 * @author ci010
 */
@SuppressWarnings("unchecked")
public class GenericUtil
{
	/**
	 * Get the Type of an object's generic parameter.
	 *
	 * @param obj       The object will be checked.
	 * @param parameter The number of its generic parameter will be checked.
	 * @return The Type of the parameter of the object.
	 */
	public static Type getGenericType(Object obj, int parameter)
	{
		return ((ParameterizedType) obj.getClass()
				.getGenericSuperclass())
				.getActualTypeArguments()
				[parameter];
	}

	/**
	 * Get the Type of the first generic parameter of the object.
	 *
	 * @param obj The object will be checked.
	 * @return The Type of the parameter of the object.
	 */
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

	public static <T> T[] cast(Object[] arr)
	{
		return (T[]) arr;
	}

	public static <T> T cast(Object o)
	{
		return (T) o;
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

