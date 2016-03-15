package net.simplelib.common.nbt;

import net.minecraft.nbt.NBTTagCompound;

/**
 * @author ci010
 */
public interface TagDeserializer<T>
{
	T deserialize(NBTTagCompound base, Class<T> type);
}
