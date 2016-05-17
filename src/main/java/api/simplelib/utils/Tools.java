package api.simplelib.utils;


import java.io.*;
import java.lang.reflect.*;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ci010
 */
public class Tools
{
	public static void where()
	{
		for (StackTraceElement s : new Throwable().getStackTrace())
			System.err.println(s);
	}

	public static <T extends AccessibleObject> T setAccessible(T t)
	{
		t.setAccessible(true);
		return t;
	}

	public static <Src, To> To proxy(Src src, To to, int i)
	{
		Field fasrc[] = src.getClass().getDeclaredFields(), fato[] = to
				.getClass().getDeclaredFields();
		int index = -1;
		for (Field fsrc : fasrc)
		{
			if (++index == i)
				return to;
			if (Modifier.isStatic(fsrc.getModifiers()) || Modifier.isFinal(fsrc.getModifiers()))
				continue;
			if (isSubclass(fato[index].getType(), fsrc.getType()))
				try
				{
					setAccessible(fato[index]).set(to, setAccessible(fsrc).get(src));
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
		}
		return to;
	}

	public static <T> T[] toArray(List<T> list, Class<T> type)
	{
		return list.toArray((T[]) Array.newInstance(type, list.size()));
	}

	public static boolean isSubclass(Class<?> supers, Class<?> clazz)
	{
		do
			if (supers == clazz)
				return true;
		while ((clazz = clazz.getSuperclass()) != null);
		return false;
	}

	public static boolean isInstance(Class<?> supers, Class<?> clazz)
	{
		for (Class<?> i : clazz.getInterfaces())
			if (i == supers)
				return true;
		return isSubclass(supers, clazz);
	}

	public static PrintWriter getPrintWriter(String path)
	{
		File file = new File(path);
		if (!file.exists())
			try
			{
				file.createNewFile();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		try
		{
			return new PrintWriter(path, "utf-8");
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}

	public static PrintWriter getPrintWriter(String path, boolean append)
	{
		File file = new File(path);
		if (!file.exists())
			try
			{
				file.createNewFile();
			}
			catch (IOException e)
			{
				System.err.println(file);
				e.printStackTrace();
			}
		try
		{
			return new PrintWriter(new OutputStreamWriter(new FileOutputStream(
					path, append), "utf-8"));
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}

	public static <T> T isNullOr(T t, T or)
	{
		return t == null ? or : t;
	}

	public static String isEmptyOr(String str, String or)
	{
		return str == null || str.isEmpty() ? or : str;
	}

	public static <T> T get(Class cls, int index)
	{
		return get(cls, index, null);
	}

	public static <T> T get(Class cls, int index, Object obj)
	{
		try
		{
			return TypeUtils.cast(setAccessible(cls.getDeclaredFields()[index]).get(obj));
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}

	public static void set(Class cls, int index, Object to)
	{
		set(cls, index, null, to);
	}

	public static void set(Class cls, int index, Object src, Object to)
	{
		try
		{
			setAccessible(cls.getDeclaredFields()[index]).set(src, to);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static Method searchMethod(Class<?> clazz, Class<?>... args)
	{
		method_forEach:
		for (Method method : clazz.getDeclaredMethods())
		{
			Class ca[] = method.getParameterTypes();
			if (ca.length == args.length)
			{
				for (int i = 0; i < ca.length; i++)
				{
					if (ca[i] != args[i])
						continue method_forEach;
				}
				return method;
			}
		}
		throw new RuntimeException("Can't search Method: " + args + ", in: " + clazz);
	}


	public static String getString(char c, int len)
	{
		char[] chars = new char[len];
		Arrays.fill(chars, c);
		return new String(chars);
	}

	public static Map<String, String> getMapping(String str)
	{
		Map<String, String> result = new LinkedHashMap<String, String>();
		for (String line : str.split("\n"))
		{
			String sa[] = line.split("=");
			if (sa.length == 2)
				result.put(sa[0], sa[1]);
		}
		return result;
	}
}
