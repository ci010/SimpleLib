package net.simplelib.registry;

import net.simplelib.HelperMod;
import net.simplelib.abstracts.ArgumentHelper;
import net.simplelib.annotation.field.Construct;
import net.simplelib.util.GenericUtil;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * @author ci010
 */
public abstract class ReflectionMaker<Input, Output>
{
	private Map<Class<? extends Annotation>, ArgumentHelper> map;

	public ReflectionMaker(Map<Class<? extends Annotation>, ArgumentHelper> map)
	{
		this.map = map;
	}

	/**
	 * Construct the field if it's null.
	 * Extract the value and wrap it into the output type.
	 *
	 * @param f The field will be extracted.
	 * @return The output
	 */
	public Output make(Field f)
	{
		Input item = null;
		Construct ctr = f.getAnnotation(Construct.class);
		boolean needSetValue = true;

		if (ctr != null)
		{
			Object[] args = new Object[]{};

			for (Annotation a : f.getAnnotations())
				if (map.containsKey(a.annotationType()))
				{
					args = map.get(a.annotationType()).getArguments(a);
					break;
				}
			int length = args.length;
			Class<?>[] argType = new Class<?>[length];

			for (int i = 0; i < length; i++)
			{
				Class<?> c = args[i].getClass();
				if (c.equals(Float.class))
					c = float.class;
				else if (c.equals(Integer.class))
					c = int.class;
				else if (c.equals(Boolean.class))
					c = boolean.class;
				else if (c.equals(Double.class))
					c = double.class;
				else if (c.equals(Long.class))
					c = long.class;
				else if (c.equals(Short.class))
					c = short.class;
				argType[i] = c;
			}
			try
			{
				Class<? extends Input> temp = GenericUtil.cast(ctr.value());
				item = temp.getConstructor(argType).newInstance(args);
			}
			catch (InstantiationException e1)
			{
				e1.printStackTrace();
			}
			catch (IllegalAccessException e1)
			{
				e1.printStackTrace();
			}
			catch (IllegalArgumentException e1)
			{
				e1.printStackTrace();
			}
			catch (InvocationTargetException e1)
			{
				e1.printStackTrace();
			}
			catch (NoSuchMethodException e1)
			{
				for (Object o : args)
					HelperMod.LOG.fatal(o);
				if (args.length == 0)
					HelperMod.LOG.fatal("args is NULL!!!");
				for (Class<?> c : argType)
					HelperMod.LOG.fatal(c.getName());
				HelperMod.LOG.fatal("not found constructor in {} ", ctr.value().getName());
			}
			catch (SecurityException e1)
			{
				e1.printStackTrace();
			}
		}
		else if (!f.isAnnotationPresent(Construct.Ignore.class))
			try
			{
				item = GenericUtil.cast(f.get(null));
				needSetValue = false;
			}
			catch (IllegalArgumentException e)
			{
				e.printStackTrace();
			}
			catch (IllegalAccessException e)
			{
				e.printStackTrace();
			}

		if (item == null)
		{
			HelperMod.LOG.fatal("Item Field {}'s value is null. It will not be registied!", f.getName());
			return null;
		}

		if (needSetValue)
			try
			{
				f.set(null, item);
			}
			catch (IllegalArgumentException e)
			{
				e.printStackTrace();
			}
			catch (IllegalAccessException e)
			{
				e.printStackTrace();
			}

		return this.warp(f, item);
	}

	/**
	 * Wrap the value of the field into certain type.
	 *
	 * @param target The value of the field.
	 * @return The standard output for this value.
	 */
	protected abstract Output warp(Field f, Input target);
}
