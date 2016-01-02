package net.simplelib.status;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;
import net.simplelib.common.NBTSeril;

/**
 * A general implementation of {@link IExtendedEntityProperties}
 * <p/>
 * Highly recommend defining an individual class to implement this.
 * <p/>
 * Use
 *
 * @author ci010
 */
public class Status implements IExtendedEntityProperties, NBTSeril
{
	protected String id;

	protected int max, ObjId;

	protected Entity entity;

	public Status(int max)
	{
		this.max = max;
	}

	protected void registerDataWatcher(Entity entity, int temp)
	{
		if (temp > 31)
			return;
		try
		{
			entity.getDataWatcher().addObject(temp, this.max);
		}
		catch (IllegalArgumentException e)
		{
			if (e.getMessage().equals("Duplicate windowId value for " + temp + "!"))
				registerDataWatcher(entity, ++temp);
		}
		this.ObjId = temp;
	}

	/**
	 * get max value of this status from this status obj
	 */
	public int getMax()
	{
		return this.max;
	}

	/**
	 * get current value of this status from data watcher
	 */
	public int getCurrent()
	{
		return this.entity.getDataWatcher().getWatchableObjectInt(this.ObjId);
	}

	@Override
	public final void saveNBTData(NBTTagCompound compound)
	{
		NBTTagCompound properties = new NBTTagCompound();
		this.writeToNBT(properties);
		compound.setTag(this.id, properties);
	}

	@Override
	public final void loadNBTData(NBTTagCompound compound)
	{
		NBTTagCompound properties = (NBTTagCompound) compound.getTag(this.id);
		this.readFromNBT(properties);
	}

	/**
	 * set current value of this status to data watcher
	 */
	protected void setCurrent(int value)
	{
		if (value > max) return;
		this.entity.getDataWatcher().updateObject(this.ObjId, value);
	}

	@Override
	public void init(Entity entity, World world)
	{
		this.entity = entity;
		this.registerDataWatcher(entity, 20);
	}


	public Status setMax(int max)
	{
		this.max = max;
		setCurrent(this.max);
		return this;
	}

	@Override
	public void readFromNBT(NBTTagCompound tag)
	{
		this.max = tag.getInteger("max");
		this.setCurrent(tag.getInteger("current"));
	}

	@Override
	public void writeToNBT(NBTTagCompound tag)
	{
		tag.setInteger("current", getCurrent());
		tag.setInteger("max", max);
	}
}
