package test.api.component.block.module;

import net.minecraft.entity.Entity;
import net.minecraft.util.BlockPos;
import test.api.world.World;

/**
 * @author ci010
 */
public interface HandlerFall
{
	void onFallenUpon(World worldIn, BlockPos pos, Entity entityIn, float fallDistance);

	void onLanded(World worldIn, Entity entityIn);
}
