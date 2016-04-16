package test.api.component.block.module;

import net.minecraft.util.BlockPos;
import test.api.component.block.StateBlock;
import test.api.component.entity.StateEntity;
import test.api.world.World;

/**
 * @author ci010
 */
public interface Collision
{
	void onEntityCollidedWithBlock(World worldIn, BlockPos pos, StateBlock state, StateEntity entityIn);
}
