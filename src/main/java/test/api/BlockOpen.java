package test.api;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;

import java.util.List;

/**
 * @author ci010
 */
class BlockOpen extends Block
{
	public BlockOpen(Material materialIn)
	{
		super(materialIn);
	}

	@Override
	protected List<ItemStack> captureDrops(boolean start)
	{
		return super.captureDrops(start);
	}

	@Override
	protected BlockState createBlockState()
	{
		return super.createBlockState();
	}

	@Override
	public ItemStack createStackedBlock(IBlockState state)
	{
		return super.createStackedBlock(state);
	}

}
