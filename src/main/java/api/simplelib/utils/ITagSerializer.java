package api.simplelib.utils;

import net.minecraft.nbt.NBTTagCompound;

/**
 * @author ci010
 */
public interface ITagSerializer<T>
{
	T readFromNBT(NBTTagCompound tag, T data);

	void writeToNBT(NBTTagCompound tag, T data);
}
