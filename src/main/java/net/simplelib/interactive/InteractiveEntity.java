package net.simplelib.interactive;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import net.minecraft.inventory.Slot;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.simplelib.common.nbt.ITagSerial;
import net.simplelib.common.VarSync;

import java.util.List;

/**
 * @author ci010
 */
public class InteractiveEntity implements ITagSerial
{
	protected World world;
	protected String id;
	protected ImmutableList<VarSync> sync;
	protected ImmutableMap<String, Inventory> inventories;
	protected List<InventoryManager.Info> infoList;

	protected InteractiveEntity(String id, World world)
	{
		this.id = id;
		this.world = world;
	}

	public ContainerCommon loadToContainer(ContainerCommon container)
	{
		List<Slot> slots = Lists.newArrayList();
		Inventory inventory;
		for (InventoryManager.Info info : infoList)
			if (info instanceof InventoryManager.SpaceInfo)
			{
				InventoryManager.SpaceInfo spaceInfo = (InventoryManager.SpaceInfo) info;
				inventory = inventories.get(spaceInfo.id);
				int counter = 0;
				for (int j = 0; j < spaceInfo.column; ++j)
					for (int k = 0; k < spaceInfo.row; ++k)
						slots.add(new SlotWeak(inventory, counter++, spaceInfo.x + 18 * j, spaceInfo.y + 18 * k));
			}
			else
				slots.add(new SlotStrong(inventory = inventories.get("default"), info.rule, inventory.namespace
						.indexOf(info.id), info.x, info.y));
		return container.loadSlots(slots).load(ImmutableList.copyOf(sync));
	}

	protected InteractiveEntity loadInventory(ImmutableMap<String, Inventory> inventories)
	{
		this.inventories = inventories;
		return this;
	}

	@Override
	public void readFromNBT(NBTTagCompound tag)
	{
		this.id = tag.getString("interactive_id");
		for (VarSync syncNBT : this.sync)
			syncNBT.get().readFromNBT(tag.getCompoundTag("sync"));
		for (Inventory inventory : this.inventories.values())
			inventory.readFromNBT(tag);
	}

	@Override
	public void writeToNBT(NBTTagCompound tag)
	{
		tag.setString("interactive_id", this.id);
		NBTTagCompound sync = new NBTTagCompound();
		for (VarSync syncNBT : this.sync)
			syncNBT.get().writeToNBT(sync);
		tag.setTag("sync", sync);
		for (Inventory inventory : this.inventories.values())
			inventory.writeToNBT(tag);
	}

}
