package api.simplelib.seril;

import api.simplelib.utils.NotNull;
import api.simplelib.utils.Nullable;
import net.minecraft.nbt.NBTTagCompound;

/**
 * @author ci010
 */
public interface ITagSerializer<T>
{
	@NotNull
	T readFromNBT(NBTTagCompound tag, @Nullable T data);

	void writeToNBT(NBTTagCompound tag, T data);
}
