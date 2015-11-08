package net.ci010.minecrafthelper.test;

import com.google.common.collect.Sets;
import net.minecraftforge.fml.common.discovery.ASMDataTable;
import net.minecraftforge.fml.common.event.FMLStateEvent;

import java.lang.annotation.Annotation;
import java.util.Set;

/**
 * @author ci010
 */
public abstract class RegistryBuffer<T, Event extends FMLStateEvent>
{
	Set<T> cache = Sets.newHashSet();

	protected abstract Class<? extends Annotation> annotationType();

	protected abstract void parse(ASMDataTable.ASMData data);

	public void addCache(T data)
	{
		cache.add(data);
	}

	public abstract void invoke(Event state);
}
