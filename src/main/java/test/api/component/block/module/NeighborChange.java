package test.api.component.block.module;

import net.minecraft.util.BlockPos;
import test.api.component.block.ComponentBlock;
import test.api.component.block.StateBlock;
import test.api.world.World;

/**
 * @author ci010
 */
public interface NeighborChange
{
	/**
	 * Called when a neighboring block changes.
	 */
	void onNeighborBlockChange(World world, BlockPos pos, StateBlock state, ComponentBlock neighborBlock);
}
