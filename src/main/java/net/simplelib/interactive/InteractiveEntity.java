package net.simplelib.interactive;

import api.simplelib.interactive.Interactive;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import api.simplelib.VarSync;
import net.simplelib.common.nbt.ITagSerial;
import net.simplelib.interactive.inventory.Inventory;
import net.simplelib.interactive.inventory.InventoryManagerImpl;

import java.util.List;

/**
 * @author ci010
 */
public class InteractiveEntity implements ITagSerial
{
	protected ImmutableList<VarSync> sync;
	protected ImmutableMap<String, Inventory> inventories;
	protected List<InventoryManagerImpl.Info> infoList;

	protected World world;
	protected String id;
	private BlockPos pos;
	private List<ITagSerial> properties;
	private Interactive real;

	protected InteractiveEntity(Interactive real, String id, World world, List<ITagSerial> properties)
	{
		this.real = real;
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

	public ITagSerial get(Class<?> clz)
	{
		for (ITagSerial property : this.properties)
			if (clz.isAssignableFrom(property.getClass()))
				return property;
		return null;
	}

	public int add(Object object)
	{
//		this.properties.add(object);
		return this.properties.size() - 1;
	}

	public void interactWith(EntityPlayer player)
	{
		this.real.interactWith(player, this.pos);
	}

	@Override
	public void readFromNBT(NBTTagCompound tag)
	{
		this.id = tag.getString("interactive_id");
		if (properties != null)
			for (ITagSerial property : properties)
				property.readFromNBT(tag);
		for (VarSync syncNBT : this.sync)
			syncNBT.get().readFromNBT(tag.getCompoundTag("sync"));
		for (Inventory inventory : this.inventories.values())
			inventory.readFromNBT(tag);
	}

	@Override
	public void writeToNBT(NBTTagCompound tag)
	{
		tag.setString("interactive_id", this.id);
		if (properties != null)
			for (ITagSerial property : properties)
				property.writeToNBT(tag);
		NBTTagCompound sync = new NBTTagCompound();
		for (VarSync syncNBT : this.sync)
			syncNBT.get().writeToNBT(sync);
		tag.setTag("sync", sync);
		for (Inventory inventory : this.inventories.values())
			inventory.writeToNBT(tag);
	}

}
