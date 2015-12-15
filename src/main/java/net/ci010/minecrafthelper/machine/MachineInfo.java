package net.ci010.minecrafthelper.machine;

import net.ci010.minecrafthelper.interactive.InteractiveMeta;

/**
 * @author ci010
 */
public abstract class MachineInfo extends InteractiveMeta
{
	BlockMachine block;

	public MachineInfo setBlock(BlockMachine block)
	{
		this.block = block;
		return this;
	}
}
