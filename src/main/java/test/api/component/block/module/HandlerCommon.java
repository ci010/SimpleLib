package test.api.component.block.module;

import net.minecraft.util.BlockPos;
import net.minecraft.world.Explosion;
import test.api.component.block.StateBlock;
import test.api.world.World;

/**
 * @author ci010
 */
public interface HandlerCommon
{
	boolean onBlockEventReceived(World worldIn, BlockPos pos, StateBlock state, int eventID, int eventParam);

	void onBlockExploded(World world, BlockPos pos, Explosion explosion);
}
