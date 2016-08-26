package api.simplelib.seril;

import net.minecraft.nbt.NBTTagCompound;

/**
 * The bus style serialization. This interface indicates that this object could serialize by itself.
 * <p>You will get a tag
 * compound which contains many other tag parallel to this object, and
 * you will read/write on this tag compound.</p>
 *
 * @author ci010
 * @see INBTSerializer
 */
public interface ICompoundSerializable
{
	void readFromNBT(NBTTagCompound tag);

	void writeToNBT(NBTTagCompound tag);

	interface Delegate<T>
	{
		void readFromNBT(T data, NBTTagCompound tag);

		void writeToNBT(T data, NBTTagCompound tag);
	}
}
