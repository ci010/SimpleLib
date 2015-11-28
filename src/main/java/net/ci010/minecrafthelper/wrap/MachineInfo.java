package net.ci010.minecrafthelper.wrap;

import com.google.common.collect.*;
import net.ci010.minecrafthelper.HelperMod;
import net.ci010.minecrafthelper.test.*;
import net.minecraft.item.ItemStack;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author ci010
 */
public class MachineInfo
{
	/**
	 * Name of the MachineInfo associated with tileEntity too.
	 */
	String name;

	Gui gui = new Gui();

	class Gui
	{
		List<GuiComponent> gui = Lists.newArrayList();

		public Gui setBackground(TileTexture texture)
		{
			gui.add(texture);
			return this;
		}

		public Gui addString(String s, int x, int y)
		{
			gui.add(new GuiString(s, x, y));
			return this;
		}

		public Gui addDynamicString(VarInteger var, int x, int y)
		{

			return this;
		}

		public Gui addBar(ModBar bar)
		{
			this.gui.add(bar);
			return this;
		}
	}

	public MachineInfo.Gui getGui()
	{
		return this.gui;
	}


	public MachineInfo setName(String s)
	{
		this.name = s;
		return this;
	}

	Map<Class<? extends MachineProcess>, ProcessInfo> processInfoMap = Maps.newHashMap();

	public MachineInfo addProcess(Class<? extends MachineProcess> process)
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
		Set<Field> stacks, integers, sync;

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
}
