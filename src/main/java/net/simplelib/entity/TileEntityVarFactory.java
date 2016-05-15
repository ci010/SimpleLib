package net.simplelib.entity;

import api.simplelib.Var;
import api.simplelib.VarFactory;
import api.simplelib.VarSync;
import api.simplelib.VarSyncBase;
import api.simplelib.seril.ITagSerializable;
import api.simplelib.seril.ITagSerializer;
import com.google.common.collect.ImmutableList;
import net.minecraft.nbt.NBTTagCompound;
import net.simplelib.common.VarSyncPrimitive;

/**
 * @author ci010
 */
public class TileEntityVarFactory implements VarFactory
{
	private ImmutableList.Builder<ITagSerializable> varCache = ImmutableList.builder();

	@Override
	public VarSync<Integer> newInteger(String name, int i)
	{
		VarSyncPrimitive<Integer> var = new VarSyncPrimitive<Integer>(name, i);
		varCache.add(var);
		return var;
	}

	@Override
	public VarSync<Float> newFloat(String name, float f)
	{
		VarSyncPrimitive<Float> var = new VarSyncPrimitive<Float>(name, f);
		varCache.add(var);
		return var;
	}

	@Override
	public VarSync<Short> newShort(String name, short l)
	{
		VarSyncPrimitive<Short> var = new VarSyncPrimitive<Short>(name, l);
		varCache.add(var);
		return var;
	}

	@Override
	public VarSync<Byte> newByte(String name, byte b)
	{
		VarSyncPrimitive<Byte> var = new VarSyncPrimitive<Byte>(name, b);
		varCache.add(var);
		return var;
	}

	@Override
	public VarSync<String> newString(String name, String s)
	{
		VarSyncPrimitive<String> var = new VarSyncPrimitive<String>(name, s);
		varCache.add(var);
		return var;
	}

	@Override
	public VarSync<Double> newDouble(String name, double d)
	{
		VarSyncPrimitive<Double> var = new VarSyncPrimitive<Double>(name, d);
		varCache.add(var);
		return var;
	}

	@Override
	public <T extends Enum<T>> VarSync<T> newEnum(String name, T e, Class<T> enumClass)
	{
		VarSyncBase<T> var = new VarSyncBase<T>()
		{
			@Override
			public void readFromNBT(NBTTagCompound tag)
			{
				this.load(Enum.valueOf(this.get().getDeclaringClass(), tag.getString("enum-name")));
			}

			@Override
			public void writeToNBT(NBTTagCompound tag)
			{
				tag.setString("enum-name", this.get().name());
			}
		};
		varCache.add(var);
		return var;
	}

	@Override
	public <T> VarSync<T> newVar(T init, final ITagSerializer<T> serializer)
	{
		VarSyncBase<T> base = new VarSyncBase<T>()
		{
			@Override
			public void readFromNBT(NBTTagCompound tag)
			{
				serializer.readFromNBT(tag, this.data);
			}

			@Override
			public void writeToNBT(NBTTagCompound tag)
			{
				serializer.writeToNBT(tag, this.data);
			}
		};
		base.set(init);
		varCache.add(base);
		return base;
	}
}
