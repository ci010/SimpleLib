package net.simplelib.common;

import api.simplelib.VarSync;
import api.simplelib.utils.GenericUtil;
import net.minecraft.nbt.NBTTagCompound;
import net.simplelib.common.nbt.NBTBasement;

/**
 * @author ci010
 */
public class VarSyncPrimitive<T> extends VarSync<T>
{
	private String id;

	public VarSyncPrimitive(String id, T data)
	{
		this.id = id;
		this.set(data);
	}

	@Override
	public void readFromNBT(NBTTagCompound tag)
	{
		if (tag.getTag(id) == null)
			return;
		this.set(GenericUtil.<T>cast(NBTBasement.instance().deserialize(tag.getTag(id))));
	}

	@Override
	public void writeToNBT(NBTTagCompound tag)
	{
		tag.setTag(id, NBTBasement.instance().serialize(this.get()));
	}
}
