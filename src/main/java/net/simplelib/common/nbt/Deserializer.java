package net.simplelib.common.nbt;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;

/**
 * @author ci010
 */
public interface Deserializer<T>
{
	T deserialize(NBTBase base);

	interface Tag<T>
	{
		T deserialize(NBTTagCompound tag, Class<T> type);
	}
}
