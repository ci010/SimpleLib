package net.simplelib.entity;

import api.simplelib.Var;
import api.simplelib.entity.IStatus;
import api.simplelib.VarFactory;
import api.simplelib.utils.GenericUtil;
import com.google.common.collect.ImmutableList;
import net.minecraft.entity.DataWatcher;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;
import net.simplelib.common.nbt.ITagSerial;
import net.simplelib.common.nbt.NBTBasement;

import java.util.List;

/**
 * A general implementation of {@link IExtendedEntityProperties}
 * <p/>
 * Highly recommend defining an individual class to implement this.
 * <p/>
 * Use
 *
 * @author ci010
 */
public class Status implements IExtendedEntityProperties
{
	protected String id;

	private int currentId = 21;

	private IStatus real;

	protected Entity entity;

	private List<VarWatchingImpl> watching;

	public static Status get(Entity entity, String id)
	{
		return (Status) IPropertiesManager.instance().get(entity, id);
	}

	protected int registerDataWatcher(Object o)
	{
		if (currentId >= 31)
			return -1;
		boolean retry;
		do
		{
			retry = false;
			try
			{
				entity.getDataWatcher().addObject(++currentId, o);
			}
			catch (IllegalArgumentException e)
			{
				if (e.getMessage().equals("Duplicate windowId value for " + currentId + "!"))
					retry = true;
				else
					return -1;
			}
		}
		while (retry);
		return currentId;
	}

	public Status(IStatus status)
	{
		this.real = status;
	}

	@Override
	public final void saveNBTData(NBTTagCompound compound)
	{
		NBTTagCompound properties = new NBTTagCompound();
		real.writeToNBT(properties);
		if (this.watching == null)
			return;
		for (VarWatchingImpl var : this.watching)
			var.writeToNBT(properties);
		compound.setTag(this.id, properties);
	}

	@Override
	public final void loadNBTData(NBTTagCompound compound)
	{
		NBTTagCompound properties = (NBTTagCompound) compound.getTag(this.id);
		if (properties == null)
			properties = new NBTTagCompound();
		real.readFromNBT(properties);
		if (this.watching == null)
			return;
		for (VarWatchingImpl var : this.watching)
			var.readFromNBT(properties);
	}


	@Override
	public final void init(Entity entity, World world)
	{
		this.entity = entity;
		final ImmutableList.Builder<VarWatchingImpl> builder = ImmutableList.builder();
		real.build(entity, new VarFactory()
		{
			@Override
			public VarWatchingImpl<Integer> newInteger(String name, int i)
			{
				VarWatchingImpl<Integer> var = new VarWatchingImpl<Integer>(name, i)
				{
					@Override
					public Integer get() {return getDelegate().getWatchableObjectInt(this.getId());}
				};
				builder.add(var);
				return var;
			}

			@Override
			public VarWatchingImpl<Float> newFloat(String name, float f)
			{
				VarWatchingImpl<Float> var = new VarWatchingImpl<Float>(name, f)
				{
					@Override
					public Float get() {return this.getDelegate().getWatchableObjectFloat(this.getId());}
				}; builder.add(var);
				return var;
			}

			@Override
			public VarWatchingImpl<Short> newShort(String name, short s)
			{
				VarWatchingImpl<Short> var = new VarWatchingImpl<Short>(name, s)
				{
					@Override
					public Short get() {return this.getDelegate().getWatchableObjectShort(this.getId());}
				}; builder.add(var);
				return var;
			}

			@Override
			public VarWatchingImpl<Byte> newByte(String name, byte b)
			{
				VarWatchingImpl<Byte> var = new VarWatchingImpl<Byte>(name, b)
				{
					@Override
					public Byte get() {return this.getDelegate().getWatchableObjectByte(this.getId());}
				}; builder.add(var);
				return var;
			}

			@Override
			public VarWatchingImpl<String> newString(String name, String s)
			{
				VarWatchingImpl<String> var = new VarWatchingImpl<String>(name, s)
				{
					@Override
					public String get() {return this.getDelegate().getWatchableObjectString(this.getId());}
				}; builder.add(var);
				return var;
			}
		});
		this.watching = builder.build();
	}

	public Var get(int idx)
	{
		return watching.get(idx);
	}

	public abstract class VarWatchingImpl<T> implements Var<T>, ITagSerial
	{
		private DataWatcher delegate;
		private String name;

		private int id;

		protected VarWatchingImpl(String name, T data)
		{
			if (data == null)
				throw new NullPointerException("The initial data cannot be null!");
			this.name = name;
			this.delegate = entity.getDataWatcher();
			this.id = registerDataWatcher(data);
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
			T t = this.get();
			this.set(GenericUtil.<T>cast(NBTBasement.instance().deserialize(tag.getTag(this.name))));
		}

		@Override
		public void writeToNBT(NBTTagCompound tag)
		{
			tag.setTag(this.name, NBTBasement.instance().serialize(this.get()));
		}


	}
}
