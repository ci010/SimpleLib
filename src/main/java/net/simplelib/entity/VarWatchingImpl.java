package net.simplelib.entity;

import api.simplelib.Var;
import api.simplelib.VarSyncBase;
import api.simplelib.utils.GenericUtil;
import api.simplelib.seril.ITagSerializable;
import net.minecraft.entity.DataWatcher;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import api.simplelib.seril.NBTBasement;

/**
 * @author ci010
 */
public abstract class VarWatchingImpl<T> extends VarSyncBase<T> implements Var<T>, ITagSerializable
{
	private DataWatcher delegate;
	private String name;
	private int id;

	protected VarWatchingImpl(int id, DataWatcher watcher, String name, T data)
	{
		if (data == null)
			throw new NullPointerException("The initial data cannot be null!");
		if (name == null)
			throw new NullPointerException("The name cannot be null!");
		this.name = name;
		this.delegate = watcher;
		this.id = id;
	}

	public int getId()
	{
		return this.id;
	}

	protected DataWatcher getDelegate()
	{
		return this.delegate;
	}

	public abstract T get();

	public void set(T data)
	{
		this.delegate.updateObject(id, data);
	}

	@Override
	public void readFromNBT(NBTTagCompound tag)
	{
		this.set(GenericUtil.<T>cast(NBTBasement.instance().deserialize(tag.getTag(this.name))));
	}

	@Override
	public void writeToNBT(NBTTagCompound tag)
	{
		NBTBase base = NBTBasement.instance().serialize(this.get());
		if (base != null)
			tag.setTag(this.name, base);
	}
}
