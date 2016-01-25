package net.simplelib.entity;

import com.google.common.collect.ImmutableList;
import net.minecraft.entity.DataWatcher;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;
import net.simplelib.common.nbt.ITagSerial;
import net.simplelib.common.utils.GenericUtil;
import net.simplelib.common.nbt.MyNBT;

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
public abstract class Status implements IExtendedEntityProperties
{
	protected String id;

	private int currentId;

	protected Entity entity;

	private List<VarWatching> watching;

	public static Status get(Entity entity, String id)
	{
		return (Status) IPropertiesManager.instance().get(entity, id);
	}

	private int registerDataWatcher(Entity entity, Object o)
	{
		if (currentId >= 31)
			return -1;
		boolean retry = false;
		do
		{
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

	@Override
	public final void saveNBTData(NBTTagCompound compound)
	{
		NBTTagCompound properties = new NBTTagCompound();
		for (VarWatching var : this.watching)
			var.writeToNBT(properties);
		compound.setTag(this.id, properties);
	}

	@Override
	public final void loadNBTData(NBTTagCompound compound)
	{
		NBTTagCompound properties = (NBTTagCompound) compound.getTag(this.id);
		if (properties == null)
			properties = new NBTTagCompound();
		for (VarWatching var : this.watching)
			var.readFromNBT(properties);
	}


	@Override
	public void init(Entity entity, World world)
	{
		ImmutableList.Builder<VarWatching> builder = ImmutableList.builder();
		this.gatherVar(builder);
		this.watching = builder.build();
		this.entity = entity;
	}

	protected abstract void gatherVar(ImmutableList.Builder<VarWatching> lst);

	private abstract class VarWatching<T> implements ITagSerial
	{
		private DataWatcher delegate;

		private int id;

		public VarWatching(T data)
		{
			this.id = registerDataWatcher(entity, data);
			this.delegate = entity.getDataWatcher();
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
			tag.setTag(String.valueOf(id), MyNBT.transfer(this.get()));
		}

		@Override
		public void writeToNBT(NBTTagCompound tag)
		{
			T data = GenericUtil.cast(MyNBT.transfer(tag.getTag(String.valueOf(id))));
			this.set(data);
		}
	}

	public class VarInteger extends VarWatching<Integer>
	{
		public VarInteger(Integer data) {super(data);}

		@Override
		public Integer get() {return getDelegate().getWatchableObjectInt(this.getId());}
	}

	public class VarFloat extends VarWatching<Float>
	{
		public VarFloat(Float data) { super(data); }

		@Override
		public Float get() { return this.getDelegate().getWatchableObjectFloat(this.getId());}
	}

	public class VarByte extends VarWatching<Byte>
	{
		public VarByte(Byte data) {super(data);}

		@Override
		public Byte get() {return this.getDelegate().getWatchableObjectByte(this.getId());}
	}

	public class VarString extends VarWatching<String>
	{
		public VarString(String data) {super(data);}

		@Override
		public String get() {return this.getDelegate().getWatchableObjectString(this.getId());}
	}

	public class VarShort extends VarWatching<Short>
	{
		public VarShort(Short data) {super(data);}

		@Override
		public Short get() {return this.getDelegate().getWatchableObjectShort(this.getId());}
	}
}
