package net.simplelib.interactive;

//import api.simplelib.interactive.meta.InteractivePropertyHook;

import test.interactive.Interactive;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import api.simplelib.seril.ITagSerializable;

/**
 * @author ci010
 */
public class InteractiveEntity implements Interactive.Entity
{
	protected World world;
	protected String id;
	private BlockPos pos;
	private ITagSerializable[] properties;
	private ICapabilityProvider[] providers;

	protected InteractiveEntity(String id, World world)
	{
		this.id = id;
		this.world = world;
		this.pos = BlockPos.ORIGIN;
	}

	protected InteractiveEntity load(ITagSerializable[] properties, ICapabilityProvider[] providers)
	{
		this.properties = properties.length == 0 ? null : properties;
		this.providers = providers.length == 0 ? null : providers;
		return this;
	}

	public void setPos(BlockPos pos)
	{
		this.pos = pos;
	}

	@Override
	public BlockPos getPos()
	{
		return this.pos;
	}

//	@Override
	public String id()
	{
		return "entity";
	}

	@Override
	public World getWorld()
	{
		return world;
	}

	public String getId()
	{
		return this.id;
	}

	@Override
	public void readFromNBT(NBTTagCompound tag)
	{
		this.id = tag.getString("interactive_id");
		if (properties != null)
			for (ITagSerializable property : properties)
				property.readFromNBT(tag);
	}

	@Override
	public void writeToNBT(NBTTagCompound tag)
	{
		tag.setString("interactive_id", this.id);
		if (properties != null)
			for (ITagSerializable property : properties)
				property.writeToNBT(tag);
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing)
	{
		if (this.properties == null)
			return false;
		for (ICapabilityProvider provider : providers)
			if (provider.hasCapability(capability, facing))
				return true;
		return false;
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing)
	{
		if (this.properties == null)
			return null;
		T data;
		for (ICapabilityProvider provider : providers)
		{
			data = provider.getCapability(capability, facing);
			if (data != null)
				return data;
		}
		return null;
	}
}
