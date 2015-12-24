package net.simplelib.interactive.machine;


import net.simplelib.interactive.Interactive;

/**
 * @author ci010
 */
public abstract class MachineInfo implements Interactive
{
	protected BlockMachine block;

	public MachineInfo setBlock(BlockMachine block)
	{
		this.block = block;
		return this;
	}
}
