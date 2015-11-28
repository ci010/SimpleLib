package net.ci010.minecrafthelper.wrap;

import net.ci010.minecrafthelper.test.UpdateSafe;

/**
 * @author ci010
 */
public interface MachineProcess extends UpdateSafe
{
	void preUpdate();

	void postUpdate();
}
