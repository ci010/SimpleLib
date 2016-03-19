package test.api.block;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

/**
 * @author ci010
 */
public interface HandlerBreak
{
	void onBlockBreak(World worldIn, BlockPos pos, IBlockState state);

	/**
	 * Called when a player destroys this Block
	 */
	void onBlockDestroyedByPlayer(World worldIn, BlockPos pos, IBlockState state);

	void onBlockHarvested(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player);
}
