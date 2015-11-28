package net.ci010.minecrafthelper.wrap;

import net.ci010.minecrafthelper.test.UpdateSafe;
import net.ci010.minecrafthelper.test.VarSync;
import net.minecraft.nbt.NBTBase;

/**
 * @author ci010
 */
public abstract class MachineProcess implements UpdateSafe
{
	public abstract void preUpdate();

	public abstract void postUpdate();
}
