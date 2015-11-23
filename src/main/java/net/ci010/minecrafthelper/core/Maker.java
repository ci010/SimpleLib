package net.ci010.minecrafthelper.core;

import net.ci010.minecrafthelper.abstracts.ArgumentHelper;
import net.ci010.minecrafthelper.annotation.field.Construct;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import static net.ci010.minecrafthelper.HelperMod.LOG;

/**
 * @author ci010
 */
public abstract class Maker<Input, Output>
{
	private Map<Class<? extends Annotation>, ArgumentHelper> map;

	public Maker(Map<Class<? extends Annotation>, ArgumentHelper> map)
	{
		this.map = map;
	}

	@SuppressWarnings("unchecked")
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
				item = ((Class<? extends Input>) ctr.value()).getConstructor(argType).newInstance(args);
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
					LOG.fatal(o);
				if (args.length == 0)
					LOG.fatal("args is NULL!!!");
				for (Class<?> c : argType)
					LOG.fatal(c.getName());
				LOG.fatal("not found constructor in {} ", ctr.value().getName());
			}
			catch (SecurityException e1)
			{
				e1.printStackTrace();
			}
		}
		else
			try
			{
				item = (Input) f.get(null);
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
			LOG.fatal("Item Field {}'s value is null. It will not be registied!", f.getName());
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

		return this.warpStruct(item);
	}

	protected abstract Output warpStruct(Input target);
}
