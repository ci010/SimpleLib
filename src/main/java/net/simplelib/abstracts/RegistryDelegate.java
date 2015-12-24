package net.simplelib.abstracts;

import com.google.common.collect.Sets;
import net.minecraftforge.fml.common.discovery.ASMDataTable;
import net.simplelib.util.ASMDataUtil;
import net.simplelib.util.GenericUtil;

import java.lang.annotation.Annotation;
import java.util.Iterator;
import java.util.Set;

/**
 * @author ci010
 */
public abstract class RegistryDelegate<T extends Annotation>
{
	protected Set<Cache> cache = Sets.newHashSet();
	private Iterator<Cache> itr;
	private Cache current;

	class Cache
	{
		Class<?> clz;
		String modid;
		T annotation;

		public Cache(Class<?> clz, String modid, T annotation)
		{
			this.clz = clz;
			this.modid = modid;
			this.annotation = annotation;
		}
	}

	public boolean hasNext()
	{
		if (this.itr == null)
			this.itr = cache.iterator();
		return itr.hasNext();
	}

	public void next()
	{
		if (this.itr == null)
			this.itr = cache.iterator();
		this.current = itr.next();
	}

	protected Class<?> getAnnotatedClass()
	{
		return current.clz;
	}

	protected String getModid()
	{
		return current.modid;
	}

	protected T getAnnotation()
	{
		return this.current.annotation;
	}

	public final void addCache(ASMDataTable.ASMData data)
	{
		Class<?> clz = ASMDataUtil.getClass(data);
		String modid = ASMDataUtil.getModId(data);
		Class<? extends T> type = GenericUtil.getGenericTypeTo(this);
		T annotation = ASMDataUtil.getAnnotation(data, type);
		cache.add(new Cache(clz, modid, annotation));
	}
}
