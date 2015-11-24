package net.ci010.minecrafthelper.test;

import com.google.common.collect.Lists;
import net.ci010.minecrafthelper.container.ContainerWrap;
import net.ci010.minecrafthelper.tileentity.TileEntityWrap;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

import java.lang.reflect.Field;
import java.util.List;

/**
 * @author ci010
 */
public class Machine
{
	/**
	 * Name of the Machine associated with tileEntity too.
	 */
	private String name;
	/**
	 * The id of the machine, if it's -1, it will be auto-sign.
	 */
	private int id = -1;

	private List<Slot> temp = Lists.newArrayList();

	private Slot[] slots;
	private ItemStack[] stacks;

	private List<Process> processes = Lists.newArrayList();
	private List<VarItemHolder> holders = Lists.newArrayList();
	private List<VarInteger> vars = Lists.newArrayList();

	private Gui gui = new Gui();

	class Gui
	{
		private List<GuiComponent> gui = Lists.newArrayList();

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

	public Machine.Gui getGui()
	{
		return this.gui;
	}


	public Machine setName(String s)
	{
		this.name = s;
		return this;
	}

	public Machine addProcess(Process process)
	{
		for (Field field : process.getClass().getDeclaredFields())
		{
			if (VarItemHolder.class.isAssignableFrom(field.getType()))
			{
				if (!field.isAccessible())
					field.setAccessible(true);
				//TODO check if private work
				try
				{
					this.holders.add((VarItemHolder) field.get(process));
				}
				catch (IllegalAccessException e)
				{
					e.printStackTrace();
				}
			}
			else if (VarInteger.class.isAssignableFrom(field.getType()))
			{
				if (!field.isAccessible())
					field.setAccessible(true);
				try
				{
					this.vars.add((VarInteger) field.get(process));
				}
				catch (IllegalAccessException e)
				{
					e.printStackTrace();
				}
			}
		}
		processes.add(process);
		return this;
	}
}
