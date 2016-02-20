package net.simplelib.common.registry;

import com.google.common.collect.ImmutableSet;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.simplelib.common.CommonLogger;
import net.simplelib.common.Maker;
import net.simplelib.common.registry.annotation.field.OreDic;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * @author ci010
 */
public class NamespaceMakerSimple implements Maker<Object, ImmutableSet<Namespace>>
{
	private boolean staticSensitve;

	public NamespaceMakerSimple staticSensitve(boolean bool)
	{
		this.staticSensitve = bool;
		return this;
	}

	@Override
	public ImmutableSet<Namespace> make(Object input)
	{
		ImmutableSet.Builder<Namespace> builder = ImmutableSet.builder();
		Class<?> clz;
		Object object;
		Namespace space;
		boolean handled = false;
		try
		{
			if (input instanceof Class)
			{
				clz = (Class<?>) input;
				object = clz.newInstance();
			}
			else
			{
				object = input;
				clz = input.getClass();
			}

			for (Field f : clz.getDeclaredFields())
			{
				if (Modifier.isStatic(f.getModifiers()))
					if (!staticSensitve)
						continue;

				Class<?> type = f.getType();
				if (Block.class.isAssignableFrom(type) || Item.class.isAssignableFrom(type))
				{
					handled = true;
					if (!f.isAccessible())
						f.setAccessible(true);
					Object obj = f.get(object);
					try
					{
						if (obj == null)
							obj = type.newInstance();
					}
					catch (InstantiationException e)
					{
						e.printStackTrace();
					}
					catch (IllegalAccessException e)
					{
						e.printStackTrace();
					}
					if (obj != null)
					{
						builder.add(space = Namespace.newSpace(f.getName(), obj));
						String ore = null;
						OreDic anno = f.getAnnotation(OreDic.class);
						if (anno != null)
							ore = anno.value();
						space.setOreName(ore);
					}
					else
					{
						//TODO handle this.
						CommonLogger.fatal("Cannot register the {}.");
					}

				}
			}
			if (!handled)
			{
				CommonLogger.fatal("The class {} is neither a block nor an item. Moreover, it doesn't contain any " +
						"block or item");
			}
		}
		catch (InstantiationException e)
		{
			e.printStackTrace();
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		return builder.build();
	}
}
