package test.api.block;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

/**
 * @author ci010
 */
public interface HandlerNeighborChange
{
	/**
	 * Called when a neighboring block changes.
	 */
	void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock);

	void onNeighborChange(IBlockAccess world, BlockPos pos, BlockPos neighbor);
}
