package test.api;

import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

/**
 * @author ci010
 */
public interface HitInfo
{
	World getWorld();

	BlockPos getPos();

	EnumFacing getSide();

	float hitX();

	float hitY();

	float hitZ();
}
