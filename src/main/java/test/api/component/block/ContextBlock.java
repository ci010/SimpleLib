package test.api.component.block;

import net.minecraft.util.BlockPos;
import test.api.component.Context;

/**
 * @author ci010
 */
public interface ContextBlock extends Context
{
	BlockPos pos();

	StateBlock getState();
}
