package net.simplelib.interactive.machine;

import com.google.common.collect.Lists;
import net.minecraft.util.BlockPos;
import net.simplelib.interactive.Interactive;

import java.util.List;

/**
 * @author ci010
 */
public abstract class MachineMultiInfo implements Interactive
{
	protected List<BlockPos> pattern;
	protected BlockMulti block;
	protected BlockSubMachine sub;

	public MachineMultiInfo addToPattern(BlockPos pos)
	{
		if (pattern == null)
			pattern = Lists.newArrayList();
		pattern.add(pos);
		return this;
	}

	public MachineMultiInfo setBlock(BlockMachine block)
	{
		if (block instanceof BlockMulti)
			this.block = (BlockMulti) block;
		else
			throw new IllegalArgumentException("The block should be BlockMulti");
		return this;
	}

	public MachineMultiInfo setSubBlock(BlockSubMachine block)
	{
		this.sub = block;
		return this;
	}
}
