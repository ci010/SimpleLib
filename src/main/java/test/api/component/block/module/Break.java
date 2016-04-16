package test.api.component.block.module;

import net.minecraft.util.BlockPos;
import test.api.component.block.StateBlock;
import test.api.world.World;

/**
 * @author ci010
 */
public interface Break
{
	void onBlockBreak(World world, BlockPos pos, StateBlock state);//Remove tileentity
}
