package net.simplelib.entity;

import api.simplelib.Var;
import api.simplelib.VarFactory;
import api.simplelib.VarSync;
import api.simplelib.VarSyncBase;
import api.simplelib.seril.ITagSerializable;
import api.simplelib.seril.ITagSerializer;
import com.google.common.collect.ImmutableList;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.ai.attributes.RangedAttribute;
import net.minecraft.nbt.NBTTagCompound;

/**
 * @author ci010
 */
public class EntityVarFactory implements VarFactory
{
	private ImmutableList.Builder<ITagSerializable> builder = ImmutableList.builder();
	private Entity entity;
	private int currentId;

	public EntityVarFactory(Entity entity)
	{
		this.entity = entity;
		currentId = 10;
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
				entity.getDataWatcher().updateObject(currentId, o);
			}
			catch (IllegalArgumentException e)
			{
				if (e.getMessage().contains("Duplicate"))
					retry = true;
				else
					throw e;
			}
		}
		while (retry);
		return currentId;
	}

	@Override
	public VarWatchingImpl<Integer> newInteger(String name, int i)
	{
		VarWatchingImpl<Integer> var = new VarWatchingImpl<Integer>(registerDataWatcher(i), entity.getDataWatcher(), name, i)
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
		VarWatchingImpl<Float> var = new VarWatchingImpl<Float>(registerDataWatcher(f), entity.getDataWatcher(), name, f)
		{
			@Override
			public Float get()
			{
				Float f = 0f;
				try
				{
					f = this.getDelegate().getWatchableObjectFloat(this.getId());
				}
				catch (NullPointerException e)
				{
					System.out.println("null for " + this.getId());
				}
				return f;
			}
		}; builder.add(var);
		return var;
	}

	@Override
	public VarWatchingImpl<Short> newShort(String name, short s)
	{
		VarWatchingImpl<Short> var = new VarWatchingImpl<Short>(registerDataWatcher(s), entity.getDataWatcher(), name, s)
		{
			@Override
			public Short get() {return this.getDelegate().getWatchableObjectShort(this.getId());}
		}; builder.add(var);
		return var;
	}

	@Override
	public VarWatchingImpl<Byte> newByte(String name, byte b)
	{
		VarWatchingImpl<Byte> var = new VarWatchingImpl<Byte>(registerDataWatcher(b), entity.getDataWatcher(), name, b)
		{
			@Override
			public Byte get() {return this.getDelegate().getWatchableObjectByte(this.getId());}
		}; builder.add(var);
		return var;
	}

	@Override
	public VarWatchingImpl<String> newString(String name, String s)
	{
		VarWatchingImpl<String> var = new VarWatchingImpl<String>(registerDataWatcher(s), entity.getDataWatcher(), name, s)
		{
			@Override
			public String get() {return this.getDelegate().getWatchableObjectString(this.getId());}
		}; builder.add(var);
		return var;
	}

	@Override
	public VarSync<Double> newDouble(final String name, double d)
	{
		VarSync<Double> var;
		if (entity instanceof EntityLivingBase)
		{
			EntityLivingBase base = (EntityLivingBase) entity;
			final IAttributeInstance attributeInstance = base.getAttributeMap().registerAttribute(new RangedAttribute(null, name, d, Double.MIN_VALUE, Double
					.MAX_VALUE).setShouldWatch(true));
			var = new VarSyncBase<Double>()
			{
				@Override
				public void readFromNBT(NBTTagCompound tag)
				{
					this.data = tag.getDouble(name);
				}

				@Override
				public void writeToNBT(NBTTagCompound tag)
				{
					tag.setDouble(name, this.data);
				}

				@Override
				public Double get()
				{
					return attributeInstance.getBaseValue();
				}

				@Override
				public void set(Double value)
				{
					attributeInstance.setBaseValue(value);
				}
			};
		}
		else
			var = new VarWatchingImpl<Double>(registerDataWatcher(Double.toString(d)), this.entity.getDataWatcher(), name, d)
			{
				@Override
				public void set(Double data)
				{
					this.getDelegate().updateObject(this.getId(), Double.toString(data));
				}

				@Override
				public Double get()
				{
					return Double.parseDouble(this.getDelegate().getWatchableObjectString(getId()));
				}
			};
		builder.add(var);
		return var;
	}

	@Override
	public <T extends Enum<T>> VarSync<T> newEnum(String name, T e, final Class<T> enumClass)
	{
		VarWatchingImpl<T> var = new VarWatchingImpl<T>(registerDataWatcher(e.name()), this.entity.getDataWatcher(), name, e)
		{
			@Override
			public void set(T data)
			{
				this.getDelegate().updateObject(this.getId(), data.name());
			}

			@Override
			public T get()
			{
				return Enum.valueOf(enumClass, this.getDelegate().getWatchableObjectString(this.getId()));
			}
		};
		this.builder.add(var);
		return var;
	}

	@Override
	public <T> VarSync<T> newVar(T init, final ITagSerializer<T> serializer)
	{
		VarSyncBase<T> sync = new VarSyncBase<T>()
		{
			@Override
			public void readFromNBT(NBTTagCompound tag)
			{
				serializer.readFromNBT(tag, this.get());
			}

			@Override
			public void writeToNBT(NBTTagCompound tag)
			{
				serializer.writeToNBT(tag, this.get());
			}
		};
		sync.set(init);
		builder.add(sync);
		return sync;
	}

	ImmutableList<ITagSerializable> getAllTracking()
	{
		return this.builder.build();
	}
}
