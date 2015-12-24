package net.simplelib.deprecated;

import com.google.common.collect.Lists;
import net.minecraft.util.BlockPos;

import java.util.List;

/**
 * The implementation of Pattern3D into BlockPos.
 *
 * @author ci010
 */
public class Pattern3DBlockPos extends Pattern3D<BlockPos>
{
	@Override
	public BlockPos nextPosition(BlockPos pos, int direction)
	{
		switch (direction)
		{
			case 1:
				return pos.north();
			case 2:
				return pos.east();
			case 3:
				return pos.south();
			case 4:
				return pos.east();
			case 5:
				return pos.up();
			case 6:
				return pos.down();
		}
		return pos;
	}

	@Override
	public BlockPos getOrigin()
	{
		return new BlockPos(0, 0, 0);
	}

	public Pattern3DBlockPos(List<BlockPos> pos)
	{
		super(pos);
	}

	/**
	 * Transfer the pattern to a specific coordination.
	 *
	 * @param origin The block position will be transfer to.
	 * @return The actual block positions.
	 */
	public List<BlockPos> transferTo(BlockPos origin)
	{
		List<BlockPos> result = Lists.newArrayList();
		for (BlockPos blockPos : sub)
			result.add(blockPos.add(origin));
		return result;
	}
}
