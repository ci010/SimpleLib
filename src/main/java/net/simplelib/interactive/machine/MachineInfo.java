package net.simplelib.interactive.machine;


import api.simplelib.interactive.Interactive;
import net.simplelib.interactive.block.BlockMachine;

/**
 * @author ci010
 */
public interface MachineInfo extends Interactive
{
	 BlockMachine getBlock();
}
