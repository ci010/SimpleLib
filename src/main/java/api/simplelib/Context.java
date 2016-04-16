package api.simplelib;

import api.simplelib.common.Nullable;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

/**
 * @author ci010
 */
public interface Context
{
	World getWorld();

	@Nullable
	BlockPos getPos();

	@Nullable
	String getId();
}
