package net.ci010.minecrafthelper.machine;

import net.ci010.minecrafthelper.abstracts.UpdateSafe;

/**
 * @author ci010
 */
public abstract class Process implements UpdateSafe
{
	public abstract void preUpdate();

	public abstract void postUpdate();
}
