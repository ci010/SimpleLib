package net.ci010.minecrafthelper.wrap;

import com.google.common.collect.Lists;
import net.ci010.minecrafthelper.test.GuiComponent;
import net.ci010.minecrafthelper.test.GuiContainerWrap;
import net.ci010.minecrafthelper.test.VarInteger;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

/**
 * @author ci010
 */
public class Machine
{
	static List<Machine> collection = Lists.newArrayList();
	static int nextId = -1;

	String name;

	Class<? extends MachineProcess>[] clz;
	MachineInfo.ProcessInfo[] info;
	MachineInfo.Gui gui;
	int numOfProcess, numOfStack, numOfInt, numOfSync;

	/**
	 * The id of the machine, if it's -1, it will be auto-sign.
	 */
	int id = -1;

	public Machine(MachineInfo info)
	{
		this.name = info.name;
		//TODO copy this integer to the tileentity and allocate with container
		this.numOfProcess = info.processInfoMap.size();
		this.clz = new Class[numOfProcess];
		this.info = new MachineInfo.ProcessInfo[numOfProcess];
		int idx = -1;
		numOfInt = numOfStack = numOfSync = 0;
		for (Map.Entry<Class<? extends MachineProcess>, MachineInfo.ProcessInfo> entry : info.processInfoMap.entrySet())
		{
			this.clz[++idx] = entry.getKey();
			MachineInfo.ProcessInfo temp = entry.getValue();
			numOfInt += temp.integers.size();
			numOfStack += temp.stacks.size();
			numOfSync += temp.sync.size();
			this.info[idx] = temp;
		}
		this.id = ++nextId;
		collection.add(this);
	}

	public TileEntityWrap getTileEntity()
	{
		MachineProcess[] procs = new MachineProcess[numOfProcess];
		int stackConut = -1, integerCount = -1;
		ItemStack[] stacks = new ItemStack[this.numOfInt];
		VarInteger[] integers = new VarInteger[this.numOfInt];

		for (int i = 0; i < numOfProcess; ++i)
		{
			MachineProcess proc;
			MachineInfo.ProcessInfo vars = this.info[i];
			try
			{
				int num = -1;
				proc = this.clz[i].newInstance();
				procs[i] = proc;
				for (Field integer : vars.integers)
					integers[++integerCount] = (VarInteger) integer.get(proc);
				for (Field stack : vars.stacks)
					stacks[++stackConut] = (ItemStack) stack.get(proc);
				// TODO: 2015/11/27 handle sync
			}
			catch (InstantiationException e)
			{
				e.printStackTrace();
			}
			catch (IllegalAccessException e)
			{
				e.printStackTrace();
			}


		}
		return new TileEntityWrap(name, stacks, integers, procs) {};
	}

	public ContainerWrap getContainer(EntityPlayer player, TileEntityWrap tile)
	{
		return new ContainerWrap(player.inventory, tile.integers)
		{
			@Override
			public boolean canInteractWith(EntityPlayer playerIn)
			{
				//TODO check the usage of this
				return true;
			}
		};
	}

	public GuiContainer getGuiContainer(EntityPlayer player, TileEntityWrap tile)
	{
		return new GuiContainerWrap(getContainer(player, tile))
		{
			@Override
			protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
			{
				for (GuiComponent guiComponent : gui.gui)
					guiComponent.draw();
			}
		};
	}

	public int getId()
	{
		return this.id;
	}
}
