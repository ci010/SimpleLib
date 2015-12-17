package net.ci010.minecrafthelper.machine.multi;

import com.google.common.collect.Lists;
import net.ci010.minecrafthelper.machine.BlockMachine;
import net.ci010.minecrafthelper.machine.MachineInfo;
import net.minecraft.util.BlockPos;

import java.util.List;

/**
 * @author ci010
 */
public class MachineMultiInfo extends MachineInfo
{
	List<BlockPos> pattern;
	BlockMulti block;
	BlockSubMachine sub;

	public MachineMultiInfo addToPattern(BlockPos pos)
	{
		if (pattern == null)
			pattern = Lists.newArrayList();
		pattern.add(pos);
		return this;
	}

	@Override
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
