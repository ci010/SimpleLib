package net.simplelib.interactive.machine;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

/**
 * @author ci010
 */
public class BlockSubMachine extends Block
{
	MachineMetaMulti machine;
	final PropertyInteger POINTER = PropertyInteger.create("pointer", 0, 6);
	final PropertyBool ADJUST = PropertyBool.create("adjust");

	protected BlockSubMachine(Material materialIn)
	{
		super(materialIn);
	}

	public BlockSubMachine()
	{
		this(Material.iron);
		this.setCreativeTab(CreativeTabs.tabDecorations);
		this.setDefaultState(this.blockState.getBaseState().withProperty(POINTER, 0).withProperty(ADJUST, false));
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		if ((Integer) state.getValue(POINTER) != 0)
		{
			BlockPos temp = getPointingPos(worldIn, pos, state);
			IBlockState other = worldIn.getBlockState(temp);
			other.getBlock().onBlockActivated(worldIn, temp, other, playerIn, side, hitX, hitY, hitZ);
			return true;
		}
		return false;
	}

	protected BlockPos getPointingPos(World worldIn, BlockPos pos, IBlockState state)
	{
		int face = (Integer) state.getValue(POINTER);
		switch (face)
		{
			case 1:
				return pos.north();
			case 2:
				return pos.east();
			case 3:
				return pos.south();
			case 4:
				return pos.west();
			case 5:
				return pos.up();
			case 6:
				return pos.down();
		}
		return pos;
	}

	@Override
	public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock)
	{
		if (machine.block == neighborBlock)
		{
			//// TODO: 2015/12/7 check if the pointing block is destroied.
		}
	}

	@Override
	protected BlockState createBlockState()
	{
		return new BlockState(this, POINTER, ADJUST);
	}

	@Override
	public int getRenderType()
	{
		return 3;
	}
}
