package net.simplelib.status;

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
public class Status implements IExtendedEntityProperties
{
	protected String id;

	protected int max, ObjId, consume, regen;

	protected Entity entity;

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
	protected void setCurrent(int value)
	{
		this.entity.getDataWatcher().updateObject(this.ObjId, value);
	}

	@Override
	public void init(Entity entity, World world)
	{
		this.entity = entity;
		this.registerDataWatcher(entity, 20);
	}

	public void replenish()
	{
		setCurrent(this.max);
	}

	public void recover()
	{
		int future = this.getCurrent() + this.regen;
		this.setCurrent(future > this.max ? this.max : future);
	}

	public void consume()
	{
		int future = this.getCurrent() - this.consume;
		this.setCurrent(future < 0 ? 0 : future);
	}

	public void setMax(int max)
	{
		this.max = max;
		setCurrent(this.max);
	}

	public void setConSpeed(int conSpeed)
	{
		this.consume = conSpeed;
	}

	public void setRegSpeed(int regSpeed)
	{
		this.regen = regSpeed;
	}
}
