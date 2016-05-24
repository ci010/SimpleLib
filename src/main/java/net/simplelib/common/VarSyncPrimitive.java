package net.simplelib.common;

import api.simplelib.utils.TypeUtils;
import api.simplelib.vars.VarSyncBase;
import net.minecraft.nbt.NBTTagCompound;
import api.simplelib.seril.NBTBasement;

/**
 * @author ci010
 */
public class VarSyncPrimitive<T> extends VarSyncBase<T>
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
		this.data = (TypeUtils.cast(NBTBasement.instance().deserialize(tag.getTag(id))));
	}

	@Override
	public void writeToNBT(NBTTagCompound tag)
	{
		tag.setTag(id, NBTBasement.instance().serialize(this.get()));
	}
}
