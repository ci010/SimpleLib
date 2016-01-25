package net.simplelib.common.registry.abstracts;

import com.google.common.collect.Sets;
import net.minecraftforge.fml.common.discovery.ASMDataTable;
import net.simplelib.common.utils.ASMDataUtil;
import net.simplelib.common.utils.GenericUtil;

import java.lang.annotation.Annotation;
import java.util.Iterator;
import java.util.Set;

/**
 * @author ci010
 */
public abstract class ASMRegistryDelegate<T extends Annotation> //extends RegistryDelegate
{
	protected Set<ASMCache> cache = Sets.newHashSet();
	private Iterator<ASMCache> itr;
	private ASMCache current;

	class ASMCache
	{
		Class<?> clz;
		String modid;
		T annotation;

		public ASMCache(Class<?> clz, String modid, T annotation)
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

	public final void addCache(String modid, ASMDataTable.ASMData data)
	{
		if (modid == null)
		{
//			System.out.println("NULL!!!!!!!!!!");
			modid = ASMDataUtil.getModId(data);
		}
		Class<?> clz = ASMDataUtil.getClass(data);
		Class<? extends T> type = GenericUtil.getGenericTypeTo(this);
		T annotation = ASMDataUtil.getAnnotation(data, type);
		cache.add(new ASMCache(clz, modid, annotation));
	}
}
