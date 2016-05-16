package api.simplelib.utils;

import net.simplelib.HelperMod;

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
			HelperMod.LOG.warn("Cannot found the generic type by superclass for {}. Now try to search from interface" +
					".", obj.getClass());
			return getInterfaceGenericType(obj, null, parameter);
		}
	}

	public static Type getGenericType(Class<?> clz, int parameter)
	{
		try
		{
			return ((ParameterizedType) clz.getGenericSuperclass())
					.getActualTypeArguments()
					[parameter];
		}
		catch (Exception e)
		{
			HelperMod.LOG.warn("Cannot found the generic type by superclass for {}. Now try to search from interface" +
					".", clz);
			return getInterfaceGenericType(clz, null, parameter);
		}
	}

	public static Type getInterfaceGenericType(Class<?> clz, Type interfaceType, int parameter)
	{
		for (Type type : clz.getGenericInterfaces())
			if (interfaceType == null || type == interfaceType)
				return ((ParameterizedType) type).getActualTypeArguments()[parameter];
			else if (type instanceof ParameterizedType)
				return ((ParameterizedType) type).getActualTypeArguments()[parameter];
		throw new IllegalArgumentException("Not found a interface with type " + interfaceType + ".");
	}

	public static Type getInterfaceGenericType(Object obj, Type interfaceType, int parameter)
	{
		return getInterfaceGenericType(obj.getClass(), interfaceType, parameter);
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

	public static Type getInterfaceGenericType(Object obj)
	{
		return getInterfaceGenericType(obj, null, 0);
	}

	public static Type getInterfaceGenericType(Class<?> clz)
	{
		return getInterfaceGenericType(clz, null, 0);
	}

	public static Type getInterfaceGenericType(Object obj, int parameter)
	{
		return getInterfaceGenericType(obj, null, parameter);
	}

	public static <T> Class<T> getInterfaceGenericTypeTo(Class<?> clz)
	{
		return getInterfaceGenericTypeTo(clz, 0);
	}

	public static <T> Class<T> getInterfaceGenericTypeTo(Class<?> clz, int parameter)
	{
		return cast(getInterfaceGenericType(clz, null, parameter));
	}

	public static <T> Class<T> getInterfaceGenericTypeTo(Class<?> clz, Type t)
	{
		return cast(getInterfaceGenericType(clz, t, 0));
	}

	public static <T> Class<T> getInterfaceGenericTypeTo(Object obj)
	{
		return getInterfaceGenericTypeTo(obj.getClass());
	}

	public static <T> Class<T> getGenericTypeTo(Object obj, int parameter)
	{
		return cast(getGenericType(obj, parameter));
	}

	public static <T> Class<T> getGenericTypeTo(Object obj)
	{
		return getGenericTypeTo(obj, 0);
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

