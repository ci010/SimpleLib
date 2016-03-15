package net.simplelib.common.nbt;

import net.minecraft.nbt.NBTTagCompound;

/**
 * @author ci010
 */
public interface ITagSerial
{
	void readFromNBT(NBTTagCompound tag);

	void writeToNBT(NBTTagCompound tag);
}
