package net.simplelib.common.nbt;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;

/**
 * @author ci010
 */
public interface Serializer<T>
{
	NBTBase serialize(T data);

	interface Primitive<T extends Number> extends Serializer<T> {}

	interface Tag<T> extends Serializer<T>
	{
		NBTTagCompound serialize(T data);
	}
}
