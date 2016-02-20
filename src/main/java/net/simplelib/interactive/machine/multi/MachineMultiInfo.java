package net.simplelib.interactive.machine.multi;

import com.google.common.collect.Lists;
import net.minecraft.util.BlockPos;
import api.simplelib.interactive.Interactive;
import net.simplelib.interactive.block.BlockMachine;

import java.util.List;

/**
 * @author ci010
 */
public abstract class MachineMultiInfo implements Interactive
{
	protected List<BlockPos> pattern;
	protected BlockMultiCore block;
	protected BlockMutltiSub sub;

	public MachineMultiInfo addToPattern(BlockPos pos)
	{
		if (pattern == null)
			pattern = Lists.newArrayList();
		pattern.add(pos);
		return this;
	}

	public MachineMultiInfo setBlock(BlockMachine block)
	{
		if (block instanceof BlockMultiCore)
			this.block = (BlockMultiCore) block;
		else
			throw new IllegalArgumentException("The block should be BlockMultiCore");
		return this;
	}

	public MachineMultiInfo setSubBlock(BlockMutltiSub block)
	{
		this.sub = block;
		return this;
	}
}
