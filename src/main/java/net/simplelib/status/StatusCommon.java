package net.simplelib.status;

import net.minecraft.nbt.NBTTagCompound;

/**
 * @author ci010
 */
public class StatusCommon extends Status
{
	protected int consume, regen;

	public StatusCommon(int max)
	{
		super(max);
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

	public void setConSpeed(int conSpeed)
	{
		this.consume = conSpeed;
	}

	public void setRegSpeed(int regSpeed)
	{
		this.regen = regSpeed;
	}

	@Override
	public void writeToNBT(NBTTagCompound compound)
	{
		compound.setInteger("consumption", consume);
		compound.setInteger("regeneration", regen);
	}

	@Override
	public void readFromNBT(NBTTagCompound compound)
	{
		this.setConSpeed(compound.getInteger("consumption"));
		this.setRegSpeed(compound.getInteger("regeneration"));
	}
}
