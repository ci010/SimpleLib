package net.ci010.minecrafthelper.interactive;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import net.ci010.minecrafthelper.HelperMod;
import net.ci010.minecrafthelper.data.VarInteger;
import net.ci010.minecrafthelper.data.VarItemHolder;
import net.ci010.minecrafthelper.data.VarSync;
import net.ci010.minecrafthelper.gui.GuiComponent;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The Builder mush build in {@link net.minecraftforge.fml.common.event.FMLInitializationEvent}
 *
 * @author ci010
 */
public abstract class InteractiveMeta
{
	/**
	 * This one should be unique
	 */
	protected String name;
	protected String modid;
	List<GuiComponent> gui = Lists.newArrayList();
	List<SlotInfo> slotInfos = Lists.newArrayList();
	Map<Class<? extends Process>, ProcessInfo> processInfoMap = Maps.newHashMap();

	public InteractiveMeta setInfo(String modid, String name)
	{
		this.name = name;
		this.modid = modid;
		return this;
	}

	public String getName()
	{
		return name;
	}

	public String getModId()
	{
		return modid;
	}

	/**
	 * Add a GuiComponent to this InteractiveComponent.
	 * <p>The position of the GuiComponent is relative to the xSize.
	 * <p>See {@link InteractiveMeta#setGuiSize(int, int)}
	 *
	 * @param component The GuiComponent will be added.
	 * @return The builder
	 */
	public InteractiveMeta addGui(GuiComponent component)
	{
		this.gui.add(component);
		return this;
	}

	/**
	 * Warning! this will affect the aline of your GuiComponents.
	 * <p>The positions of the GuiComponents are all relative to the guiLeft/guiTop.
	 * <p>guiLeft = (screenResolution - xSize)/2
	 * <p>guiRight = (screenResolution - ySize)/2
	 *
	 * @param xSize The x size of your gui
	 * @param ySize The y size of your gui
	 * @return The builder
	 */
	public InteractiveMeta setGuiSize(int xSize, int ySize)
	{
		// TODO: 2015/12/5
		return this;
	}

	/**
	 * Add a slot to the InteractiveComponent.
	 *
	 * @param name The name should match the name of the {@link VarItemHolder#name} in the processes you added to this InteractiveComponent
	 * @param x    The x position of the slot.
	 * @param y    The y position of the slot.
	 * @return The builder
	 */
	public InteractiveMeta addSlot(String name, int x, int y)
	{
		slotInfos.add(new SlotInfo(name, x, y));
		return this;
	}

	/**
	 * Add a process to the InteractiveComponent.
	 *
	 * @param process The Process will be add to this InteractiveComponent.
	 * @return The builder
	 */
	public InteractiveMeta addProcess(Class<? extends Process> process)
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
				if (VarItemHolder.class.isAssignableFrom(type))
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

	public class SlotInfo
	{
		public String name;
		public int x, y;

		public SlotInfo(String name, int x, int y)
		{
			this.name = name;
			this.x = x;
			this.y = y;
		}
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
}
