package net.simplelib.common.utils;

import net.simplelib.common.CommonLogger;

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
		try
		{
			return ((ParameterizedType) obj.getClass()
					.getGenericSuperclass())
					.getActualTypeArguments()
					[parameter];
		}
		catch (Exception e)
		{
			CommonLogger.warn("Cannot found the generic type by superclass. Now try to search from interface.");
			return getInterfaceGenericType(obj, null, parameter);
		}
	}

	public static Type getInterfaceGenericType(Object obj, Type interfaceType, int parameter)
	{
		for (Type type : obj.getClass().getGenericInterfaces())
			if (interfaceType == null || type == interfaceType || interfaceType.equals(type))
				return ((ParameterizedType) type).getActualTypeArguments()[parameter];
		throw new IllegalArgumentException("Not found a interface with type " + interfaceType + ".");
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
		return cast(getGenericType(obj, parameter));
	}

	public static <T> Class<T> getGenericTypeTo(Object obj)
	{
		return getGenericTypeTo(obj, 0);
	}

	public static Type getInterfaceGenericType(Object obj)
	{
		return getInterfaceGenericType(obj, null, 0);
	}

	public static Type getInterfaceGenericType(Object obj, int parameter)
	{
		return getInterfaceGenericType(obj, null, parameter);
	}

	public static <T> Class<T> getInterfaceGenericTypeTo(Object obj)
	{
		return cast(getInterfaceGenericType(obj));
	}

	public static Type getInterfaceGenericType(Object obj, Type interfaceType)
	{
		return getInterfaceGenericType(obj, interfaceType, 0);
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

