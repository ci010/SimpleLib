package net.simplelib.interactive;

import api.simplelib.interactive.meta.InteractivePropertyHook;
import api.simplelib.utils.GenericUtil;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.simplelib.common.nbt.ITagSerial;

import java.util.List;

/**
 * @author ci010
 */
public class InteractiveEntity implements ITagSerial
{
	protected World world;
	protected String id;
	private BlockPos pos;
	private List<ITagSerial> properties;

	protected InteractiveEntity(String id, World world, List<ITagSerial> properties)
	{
		this.id = id;
		this.world = world;
		this.pos = BlockPos.ORIGIN;
		this.properties = properties;
	}

	public void setPos(BlockPos pos)
	{
		this.pos = pos;
	}

	public BlockPos getPos()
	{
		return this.pos;
	}

	public World getWorld()
	{
		return world;
	}

	public String getId()
	{
		return this.id;
	}

	public <Data extends ITagSerial, T extends InteractivePropertyHook<Data, ?>> Data get(Class<T> propertyType)
	{
		for (ITagSerial property : this.properties)
			if (propertyType == property.getClass())
				return GenericUtil.cast(property);
		return null;
	}

	@Override
	public void readFromNBT(NBTTagCompound tag)
	{
		this.id = tag.getString("interactive_id");
		if (properties != null)
			for (ITagSerial property : properties)
				property.readFromNBT(tag);
	}

	@Override
	public void writeToNBT(NBTTagCompound tag)
	{
		tag.setString("interactive_id", this.id);
		if (properties != null)
			for (ITagSerial property : properties)
				property.writeToNBT(tag);
	}
}
