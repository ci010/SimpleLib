package net.ci010.minecrafthelper.machine;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.ci010.minecrafthelper.HelperMod;
import net.ci010.minecrafthelper.data.VarInteger;
import net.ci010.minecrafthelper.gui.Drawable;
import net.ci010.minecrafthelper.gui.GuiComponent;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

/**
 * @author ci010
 */
public class Machine extends InteractiveComponent
{
	static Map<String, Machine> map = Maps.newHashMap();

	Machine(MachineBuilder info)
	{
		super(info);
		if (map.containsKey(info.name))
		{
			HelperMod.LOG.fatal("Name duplication! There has already been a machine named {}!", info.name);
			return;
		}
		map.put(name, this);

		GameRegistry.registerBlock(new BlockContainer(info.material)
		{
			@Override
			public TileEntity createNewTileEntity(World worldIn, int meta)
			{
				return getTileEntity();
			}

			@Override
			public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ)
			{
				interactWith(playerIn, pos);
				return true;
			}
		}, "block.".concat(this.name));
	}

	public TileEntityWrap getTileEntity()
	{
		Process[] procs = new Process[numOfProcess];
		int stackConut = -1, integerCount = -1;
		ItemStack[] stacks = new ItemStack[this.numOfInt];
		VarInteger[] integers = new VarInteger[this.numOfInt];

		for (int i = 0; i < numOfProcess; ++i)
		{
			Process proc;
			MachineBuilder.ProcessInfo vars = this.info[i];
			try
			{
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
		return new TileEntityWrap().load(name, stacks, integers, procs);
	}


	@Override
	public Container getContainer(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		return new ContainerWrap(player.inventory, ((TileEntityWrap) world.getTileEntity(new BlockPos(x, y, z)))
				.integers)
		{
			@Override
			public boolean canInteractWith(EntityPlayer playerIn)
			{
				return true;
			}
		};
	}

	public static void init()
	{
		GameRegistry.registerTileEntity(TileEntityWrap.class, "tileEntityWrap");
	}

	static void linkTileEntityProcess(TileEntityWrap tile)
	{
		Machine machine = map.get(tile.getCommandSenderName());
		Process[] procs = new Process[machine.numOfProcess];
		for (int i = 0; i < machine.numOfProcess; ++i)
		{
			Process proc;
			MachineBuilder.ProcessInfo vars = machine.info[i];
			try
			{
				proc = machine.clz[i].newInstance();
				if (vars.integers != null)
					for (Field integer : vars.integers)
						integer.set(proc, tile.integers[machine.numOfInt]);
				if (vars.stacks != null)
					for (Field stack : vars.stacks)
						stack.set(proc, tile.stacks[machine.numOfStack]);
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

}
