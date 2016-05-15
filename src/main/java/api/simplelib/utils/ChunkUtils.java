package api.simplelib.utils;

import net.minecraft.util.BlockPos;
import net.minecraft.world.ChunkCoordIntPair;

/**
 * @author ci010
 */
public class ChunkUtils
{
	public static ChunkCoordIntPair getAsChunkCoord(int x, int z)
	{
		return new ChunkCoordIntPair(x >> 4, z >> 4);
	}

	public static ChunkCoordIntPair getAsChunkCoord(BlockPos pos)
	{
		return new ChunkCoordIntPair(pos.getX() >> 4, pos.getZ() >> 4);
	}
}
