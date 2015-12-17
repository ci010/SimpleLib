package net.ci010.minecrafthelper.util;

import com.google.common.collect.Lists;
import net.minecraft.util.BlockPos;

import java.util.List;

/**
 * @author ci010
 */
public class Pattern3DBlockPos extends Pattern3D<BlockPos>
{
	@Override
	public BlockPos nextBlock(BlockPos pos, int direction)
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

	public List<BlockPos> transferTo(BlockPos pos)
	{
		List<BlockPos> result = Lists.newArrayList();
		for (BlockPos blockPos : sub)
			result.add(blockPos.add(pos));
		return result;
	}
}
