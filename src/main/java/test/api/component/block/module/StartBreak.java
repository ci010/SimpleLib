package test.api.component.block.module;

import net.minecraft.util.BlockPos;
import test.api.component.block.StateBlock;
import test.api.world.World;

/**
 * @author ci010
 */
public interface StartBreak
{
	boolean startBreak(World world, BlockPos pos, StateBlock state);//TODO need to make a Hook
}
