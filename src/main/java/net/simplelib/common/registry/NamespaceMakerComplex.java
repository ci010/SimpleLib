package net.simplelib.common.registry;

import api.simplelib.component.ComponentStruct;
import com.google.common.collect.ImmutableSet;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.simplelib.HelperMod;
import net.simplelib.common.Maker;
import api.simplelib.component.ArgumentHelper;
import net.simplelib.common.registry.abstracts.ReflectionAnnotatedMaker;
import net.simplelib.common.registry.annotation.field.OreDic;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Map;

/**
 * @author ci010
 */
public class NamespaceMakerComplex implements Maker<Class<?>, ImmutableSet<Namespace>>
{
	private ReflectionAnnotatedMaker<Object, ImmutableSet<Namespace>> maker, itemBlockMaker;
	private NamespaceMakerSimple simpleMaker = new NamespaceMakerSimple();

	public NamespaceMakerComplex(Map<Class<? extends Annotation>, ArgumentHelper> map)
	{
		itemBlockMaker = new ReflectionAnnotatedMaker<Object, ImmutableSet<Namespace>>(map)
		{
			@Override
			protected ImmutableSet<Namespace> warp(Field f, Object target)
			{
				Namespace space = Namespace.newSpace(f.getName(), target);
				String ore = null;
				OreDic anno = f.getAnnotation(OreDic.class);
				if (anno != null)
					ore = anno.value();
				space.setOreName(ore);
				return ImmutableSet.of(space);
			}
		};
		maker = new ReflectionAnnotatedMaker<Object, ImmutableSet<Namespace>>(map)
		{
			@Override
			protected ImmutableSet<Namespace> warp(Field f, Object target)
			{
				return simpleMaker.make(target);
			}
		};
	}

	@Override
	public ImmutableSet<Namespace> make(Class<?> container)
	{
		ImmutableSet.Builder<Namespace> builder = ImmutableSet.builder();
		for (Field f : container.getFields())
			if (Modifier.isStatic(f.getModifiers()))
			{
				Class c = f.getType();
				if (Item.class.isAssignableFrom(c) || Block.class.isAssignableFrom(c))
					builder.addAll(itemBlockMaker.make(f));
				if (f.isAnnotationPresent(ComponentStruct.class))
					builder.addAll(maker.make(f));
			}
			else
				HelperMod.LOG.info("The field {} in container {} is not static so that it won'registerInit be constructed and registered",
						f.getName(),
						container.getName());
		return builder.build();
	}
}
