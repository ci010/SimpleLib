package net.ci010.minecrafthelper.machine;

/**
 * @author ci010
 */
public abstract class MachineInfo extends InteractiveComponentInfo
{
	BlockMachine block;

	public MachineInfo setBlock(BlockMachine block)
	{
		this.block = block;
		return this;
	}
}
