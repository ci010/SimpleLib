package test.api.component.block.module;

import net.minecraft.util.BlockPos;
import test.api.component.block.StateBlock;
import test.api.component.entity.livingbase.StatePlayer;
import test.api.world.World;

/**
 * @author ci010
 */
public interface ShouldHarvest
{
	boolean shouldBlockHarvest(World world, BlockPos pos, StateBlock state, StatePlayer player);
}
