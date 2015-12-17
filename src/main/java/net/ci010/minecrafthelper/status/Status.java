package net.ci010.minecrafthelper.status;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

/**
 * A general implementation of {@link IExtendedEntityProperties}
 * <p/>
 * Highly recommend defining an individual class to implement this.
 * <p/>
 * Use
 *
 * @author ci010
 */
public class Status<T extends Entity> implements IExtendedEntityProperties
{
	protected String id;

	protected int max, consume, regen, ObjId;

	protected T entity;

	public void setId(String id)
	{
		this.id = id;
	}

	public Status(T entity)
	{
		this.entity = entity;
	}

	public String getId()
	{
		return this.id;
	}

	protected void registerDataWatcher(Entity entity, int temp)
	{
		if (temp > 31)
			return;
		try
		{
			this.entity.getDataWatcher().addObject(temp, this.max);
		}
		catch (IllegalArgumentException e)
		{
			if (e.getMessage().equals("Duplicate id value for " + temp + "!"))
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
	public void saveNBTData(NBTTagCompound compound)
	{
		NBTTagCompound properties = new NBTTagCompound();

		properties.setInteger("current", getCurrent());
		properties.setInteger("max", max);
		properties.setInteger("consumption", consume);
		properties.setInteger("regeneration", regen);
		compound.setTag(this.id, properties);
	}

	@Override
	public void loadNBTData(NBTTagCompound compound)
	{
		NBTTagCompound properties = (NBTTagCompound) compound.getTag(this.id);

		this.setCurrent(properties.getInteger("current"));
		this.setConSpeed(properties.getInteger("consumption"));
		this.setRegSpeed(properties.getInteger("regeneration"));
		this.max = properties.getInteger("max");
	}

	/**
	 * set current value of this status to data watcher
	 */
	public void setCurrent(int value)
	{
		this.entity.getDataWatcher().updateObject(this.ObjId, value);
	}

	public void setMax(int max)
	{
		this.max = max;
		setCurrent(this.max);
	}

	public void setRegSpeed(int speed)
	{
		this.regen = speed;
	}

	public void setConSpeed(int speed)
	{
		this.regen = speed;
	}

	public void replenish()
	{
		setCurrent(this.max);
	}

	@Override
	public void init(Entity entity, World world)
	{
		registerDataWatcher(entity, 20);
	}
}
