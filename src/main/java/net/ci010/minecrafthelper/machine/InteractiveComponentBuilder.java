package net.ci010.minecrafthelper.machine;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import net.ci010.minecrafthelper.HelperMod;
import net.ci010.minecrafthelper.data.VarInteger;
import net.ci010.minecrafthelper.data.VarSync;
import net.ci010.minecrafthelper.gui.GuiComponent;
import net.minecraft.item.ItemStack;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author ci010
 */
public abstract class InteractiveComponentBuilder
{
	/**
	 * This one should be unique
	 */
	String name;
	List<GuiComponent> gui = Lists.newArrayList();
	public Map<Class<? extends Process>, ProcessInfo> processInfoMap = Maps.newHashMap();

	public InteractiveComponentBuilder addGui(GuiComponent component)
	{
		this.gui.add(component);
		return this;
	}

	public InteractiveComponentBuilder setName(String s)
	{
		this.name = s;
		return this;
	}

	public InteractiveComponentBuilder addProcess(Class<? extends Process> process)
	{
		try
		{
			process.getConstructor();
		}
		catch (NoSuchMethodException e)
		{
			HelperMod.LOG.fatal("The process should have a constructor without parameter");
			e.printStackTrace();
			return this;
		}
		for (Field field : process.getDeclaredFields())
		{
			Class<?> type = field.getType();
			if (VarSync.class.isAssignableFrom(type))
			{
				if (!field.isAccessible())
				{
					HelperMod.LOG.fatal("should be accessible");
					return this;
				}
				if (ItemStack.class.isAssignableFrom(type))
					if (!processInfoMap.containsKey(process))
						processInfoMap.put(process, new ProcessInfo().add(field, 1));
					else
						processInfoMap.get(process).add(field, 1);
				else if (VarInteger.class.isAssignableFrom(type))
					if (!processInfoMap.containsKey(process))
						processInfoMap.put(process, new ProcessInfo().add(field, 0));
					else
						processInfoMap.get(process).add(field, 0);
				else if (!processInfoMap.containsKey(process))
					processInfoMap.put(process, new ProcessInfo().add(field, 2));
				else
					processInfoMap.get(process).add(field, 2);
			}
		}
		return this;
	}

	public class ProcessInfo
	{
		public Set<Field> stacks, integers, sync;

		ProcessInfo add(Field f, int type)
		{
			switch (type)
			{
				//need to keep the order of adding
				case 0:
					if (integers == null)
						integers = Sets.newLinkedHashSet();
					integers.add(f);
				case 1:
					if (stacks == null)
						stacks = Sets.newLinkedHashSet();
					stacks.add(f);
				case 2:
					if (sync == null)
						sync = Sets.newLinkedHashSet();
					sync.add(f);
			}
			return this;
		}
	}

	public abstract InteractiveComponent build();
}
