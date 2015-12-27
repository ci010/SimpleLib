package test;

import net.minecraft.block.Block;
import net.minecraft.block.IGrowable;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

/**
 * @author ci010
 */
public class TestGrowableBlock extends Block implements IGrowable
{
	public static final PropertyInteger AGE = PropertyInteger.create("age", 0, 7);

	public TestGrowableBlock()
	{
		super(Material.wood);
		this.setDefaultState(this.blockState.getBaseState().withProperty(AGE, 0));
		this.setCreativeTab(CreativeTabs.tabFood);
		this.setUnlocalizedName("test.grow");
	}

	@Override
	public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient)
	{
		return false;
	}

	@Override
	public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state)
	{
		return false;
	}

	@Override
	public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state)
	{

	}

	public IBlockState getStateFromMeta(int meta)
	{
		return this.getDefaultState().withProperty(AGE, meta);
	}

	/**
	 * Convert the BlockState into the correct metadata value
	 */
	public int getMetaFromState(IBlockState state)
	{
		return ((Integer) state.getValue(AGE)).intValue();
	}

	protected BlockState createBlockState()
	{
		return new BlockState(this, AGE);
	}
}
