package test.api.component.block.module;

import net.minecraft.util.BlockPos;
import test.api.component.entity.livingbase.StatePlayer;
import test.api.world.World;

/**
 * @author ci010
 */
public interface LeftClick
{
	void onBlockClicked(World world, BlockPos pos, StatePlayer player);
}
