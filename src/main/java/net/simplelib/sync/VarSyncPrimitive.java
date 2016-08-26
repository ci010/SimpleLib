package net.simplelib.sync;

import api.simplelib.sync.UpdateMode;
import api.simplelib.utils.RangedHelper;
import api.simplelib.utils.TypeUtils;
import api.simplelib.sync.VarSyncBase;
import net.minecraft.nbt.NBTTagCompound;
import api.simplelib.seril.NBTBases;

/**
 * @author ci010
 */
public class VarSyncPrimitive<T> extends VarSyncBase<T>
{
	public VarSyncPrimitive(String id, T data, UpdateMode frequency)
	{
		super(id, frequency);
		this.set(data);
	}

	@Override
	public void readFromNBT(NBTTagCompound tag)
	{
		if (tag.getTag(name) == null) return;
		this.data = (TypeUtils.cast(NBTBases.instance().deserialize(tag.getTag(name))));
	}

	@Override
	public void writeToNBT(NBTTagCompound tag)
	{
		tag.setTag(name, NBTBases.instance().serialize(this.get()));
	}

	public static class Ranged<T extends Number> extends VarSyncPrimitive<T>
	{
		private RangedHelper.Primitive<T> helper;

		public Ranged(String id, T data, T max, T min, UpdateMode frequency)
		{
			super(id, data, frequency);
			helper = new RangedHelper.Primitive<T>(this, min, max);
		}

		@Override
		public void set(T data)
		{
			helper.set(data);
		}
	}

	public static class VEnum<T extends Enum<T>> extends VarSyncBase<T>
	{
		private Class<? extends Enum> type;

		public VEnum(String name, T v, UpdateMode frequency)
		{
			super(name, frequency);
			type = v.getClass();
		}

		@Override
		public void readFromNBT(NBTTagCompound tag)
		{
			this.load((T) Enum.valueOf(type, tag.getString(this.name)));
		}

		@Override
		public void writeToNBT(NBTTagCompound tag)
		{
			tag.setString(name, this.get().name());
		}
	}
}
