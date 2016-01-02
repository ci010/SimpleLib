package net.simplelib.interactive;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import net.simplelib.HelperMod;
import net.simplelib.registry.annotation.field.Name;
import net.simplelib.registry.annotation.field.Owner;
import net.simplelib.common.VarSync;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author ci010
 */
public class ProcessHandler
{
	protected List<Info> infoList;
	private List<Process> cache;
	protected Interactive provider;

	protected int numOfProcess, numOfSync, numOfInt, numOfStack;

	protected ProcessHandler(Interactive provider)
	{
		this.provider = provider;
		provider.provideProcess(this);
		if (cache.size() == 0)
			return;
		infoList = new ArrayList<Info>(cache.size());
		for (Process process : cache)
		{
			Info processInfo = new Info();
			for (Field field : process.getClass().getDeclaredFields())
			{
				Class<?> type = field.getType();
				if (VarSync.class.isAssignableFrom(type))
				{
					if (!field.isAccessible())
						HelperMod.LOG.fatal("should be accessible");
					else if (VarItemHolder.class.isAssignableFrom(type))
					{
						++numOfStack;
						String name, owner = null;
						Name nameAnno = field.getAnnotation(Name.class);
						Owner ownerAnno = field.getAnnotation(Owner.class);
						if (nameAnno != null)
							name = nameAnno.value();
						else
							name = field.getName();
						if (ownerAnno != null)
							owner = ownerAnno.value();
						processInfo.add(new HolderInfo(name, owner, field));
					}
					else if (VarInteger.class.isAssignableFrom(type))
					{
						++numOfInt;
						processInfo.add(field, 0);
					}
					else if (VarSync.class.isAssignableFrom(type))
					{
						++numOfSync;
						processInfo.add(field, 1);
					}
				}
			}
			infoList.add(processInfo);
		}
		numOfProcess = infoList.size();

		cache = null;
	}

	/**
	 * Add the actual process to this interactive component.
	 *
	 * @param process The process will be added.
	 * @return The Process handler.
	 */
	public ProcessHandler addProcess(Process process)
	{
		if (cache == null)
			cache = Lists.newArrayList();
		cache.add(process);
		return this;
	}

	public boolean available()
	{
		return infoList != null;
	}

	public List<Process> buildProcess()
	{
		provider.provideProcess(this);
		List<Process> temp = this.cache;
		this.cache = null;
		return temp;
	}

	class HolderInfo
	{
		String name;
		String owner;
		Field field;

		HolderInfo(String name, String owner, Field field)
		{
			this.name = name;
			this.owner = owner;
			this.field = field;
		}
	}

	class Info
	{
		protected Set<Field> integers, sync;
		protected List<HolderInfo> holderInfos;

		void add(Field f, int type)
		{
			switch (type)
			{
				case 0:
					if (integers == null)
						integers = Sets.newLinkedHashSet();
					integers.add(f);
				case 1:
					if (sync == null)
						sync = Sets.newLinkedHashSet();
					sync.add(f);
			}
		}

		void add(HolderInfo info)
		{
			if (holderInfos == null)
				holderInfos = Lists.newArrayList();
			holderInfos.add(info);
		}

	}
}
