package net.ci010.minecrafthelper.machine;

import net.ci010.minecrafthelper.RegistryHelper;
import net.ci010.minecrafthelper.data.VarInteger;
import net.ci010.minecrafthelper.data.VarItemHolder;
import net.ci010.minecrafthelper.interactive.ContainerWrap;
import net.ci010.minecrafthelper.interactive.InteractiveComponent;
import net.ci010.minecrafthelper.interactive.Process;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.lang.reflect.Field;

/**
 * @author ci010
 */
public class Machine extends InteractiveComponent
{
	BlockMachine block;

	Machine(MachineInfo info)
	{
		super(info);
		this.block = info.block;
		if (this.block == null)
			this.block = new BlockMachine();
		block.machine = this;
		RegistryHelper.INSTANCE.registerBlock(info.getModId(), block, name);
	}

	public TileEntityWrap getTileEntity()
	{
		net.ci010.minecrafthelper.interactive.Process[] procs = new Process[numOfProcess];
		int stackConut = -1, integerCount = -1;
		VarItemHolder[] stacks = new VarItemHolder[this.numOfStack];
		VarInteger[] integers = new VarInteger[this.numOfInt];

		for (int i = 0; i < numOfProcess; ++i)
		{
			Process proc;
			MachineInfo.ProcessInfo vars = this.info[i];
			try
			{
				proc = this.clz[i].newInstance();
				procs[i] = proc;
				for (Field integer : vars.integers)
					integers[++integerCount] = (VarInteger) integer.get(proc);
				for (Field stack : vars.stacks)
					stacks[++stackConut] = (VarItemHolder) stack.get(proc);
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
		return new TileEntityWrap().load(name, stacks, integers, procs);
	}


	@Override
	public Container getContainer(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		TileEntityWrap temp = ((TileEntityWrap) world.getTileEntity(new BlockPos(x, y, z)));
		return new ContainerWrap(player.inventory, temp.integers)
		{
			@Override
			public boolean canInteractWith(EntityPlayer playerIn)
			{
				return true;
			}
		}.loadSlots(temp, temp.namespace
				, this.slotInfo);
	}

	public static void init()
	{
		GameRegistry.registerTileEntity(TileEntityWrap.class, "tileEntityWrap");
	}

//	static void linkTileEntityProcess(TileEntityWrap tile)
//	{
//		Machine machine = map.get(tile.getCommandSenderName());
//		Process[] procs = new Process[machine.numOfProcess];
//		for (int i = 0; i < machine.numOfProcess; ++i)
//		{
//			Process proc;
//			MachineInfo.ProcessInfo vars = machine.info[i];
//			try
//			{
//				proc = machine.clz[i].newInstance();
//				if (vars.integers != null)
//					for (Field integer : vars.integers)
//						integer.set(proc, tile.integers[machine.numOfInt]);
//				if (vars.stacks != null)
//					for (Field stack : vars.stacks)
//						stack.set(proc, tile.stacks[machine.numOfStack]);
//			}
//			catch (InstantiationException e)
//			{
//				e.printStackTrace();
//			}
//			catch (IllegalAccessException e)
//			{
//				e.printStackTrace();
//			}
//		}
//	}
}
