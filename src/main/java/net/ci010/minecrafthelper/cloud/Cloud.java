package net.ci010.minecrafthelper.cloud;

import net.ci010.minecrafthelper.data.VarInteger;
import net.ci010.minecrafthelper.machine.*;
import net.ci010.minecrafthelper.machine.Process;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.server.gui.IUpdatePlayerListBox;
import net.minecraft.world.World;

import java.lang.reflect.Field;

/**
 * @author ci010
 */
public class Cloud extends InteractiveComponent implements IUpdatePlayerListBox
{
	private VarInteger[] integers;
	private Process[] process;
	private ItemStack[] stacks;// TODO: 2015/12/5 use ItemHolder 

	public Cloud(InteractiveComponentInfo info)
	{
		super(info);

		process = new Process[numOfProcess];
		stacks = new ItemStack[this.numOfInt];
		integers = new VarInteger[this.numOfInt];

		int stackConut = -1, integerCount = -1;
		for (int i = 0; i < numOfProcess; ++i)
		{
			Process proc;
			InteractiveComponentInfo.ProcessInfo vars = this.info[i];
			try
			{
				proc = this.clz[i].newInstance();
				process[i] = proc;
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
	}

	@Override
	public Container getContainer(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		return new ContainerWrap(player.inventory, integers)
		{
			@Override
			public boolean canInteractWith(EntityPlayer playerIn)
			{
				return true;
			}
		};
	}

	@Override
	public void update()
	{
		for (Process proces : process)
			proces.preUpdate();
		for (Process proces : process)
			if (proces.shouldUpdate())
				proces.update();
		for (Process proces : process)
			proces.postUpdate();
	}
}
