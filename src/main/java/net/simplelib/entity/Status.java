package net.simplelib.entity;

import api.simplelib.entity.IStatus;
import api.simplelib.seril.ITagSerializable;
import api.simplelib.vars.Var;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

import java.util.List;

/**
 * A general implementation of {@link IExtendedEntityProperties}
 * <p/>
 * Highly recommend defining an individual class to implement this.
 * <p/>
 *
 * @author ci010
 */
public class Status implements IExtendedEntityProperties
{
	private String id;

	private int currentId = 21;

	protected IStatus real;

	protected Entity entity;

	private List<ITagSerializable> watching;

	public Status(String id, IStatus status)
	{
		this.real = status;
		this.id = id;
	}

	@Override
	public final void saveNBTData(NBTTagCompound compound)
	{
		NBTTagCompound properties = new NBTTagCompound();
		real.writeToNBT(properties);
		if (this.watching == null)
			return;
		for (ITagSerializable var : this.watching)
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
		for (ITagSerializable var : this.watching)
			var.readFromNBT(properties);
	}

	@Override
	public final void init(Entity entity, World world)
	{
		this.entity = entity;
		EntityVarFactory factory = new EntityVarFactory(entity);
		real.build(entity, factory);
		this.watching = factory.getAllTracking();
	}

	public Var get(int idx)
	{
		return (Var) watching.get(idx);
	}
}
