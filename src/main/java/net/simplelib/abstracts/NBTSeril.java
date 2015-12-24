package net.simplelib.abstracts;

import net.minecraft.nbt.NBTTagCompound;

/**
 * @author ci010
 */
public interface NBTSeril
{
	void readFromNBT(NBTTagCompound tag);

	void writeToNBT(NBTTagCompound tag);
}
