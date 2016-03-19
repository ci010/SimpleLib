package net.simplelib.interactive.inventory;

import api.simplelib.Var;
import api.simplelib.interactive.Interactive;
import api.simplelib.interactive.inventory.Inventory;
import api.simplelib.interactive.inventory.InventoryRule;
import api.simplelib.interactive.inventory.SlotInfo;
import api.simplelib.interactive.inventory.SpaceInfo;
import api.simplelib.interactive.meta.InteractiveProperty;
import api.simplelib.interactive.meta.ModInteractiveMeta;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.simplelib.HelperMod;
import net.simplelib.interactive.process.VarItemHolder;

import java.util.List;

/**
 * @author ci010
 */
@ModInteractiveMeta
public class InventoryManager implements Inventory.Manager,
										 InteractiveProperty<Inventory.Data, Inventory.Meta, Inventory>,
										 Inventory.Meta
{
	private String id;
	protected List<SpaceInfo> spaces;
	protected List<SlotInfo> discrete;

	@Override
	public SlotInfo newSingletonSlot(int x, int y)
	{
		if (this.discrete == null)
			discrete = Lists.newArrayList();
		SlotInfo slotInfo = new Info(0, discrete.size(), x, y);
		discrete.add(slotInfo);
		return slotInfo;
	}

	@Override
	public SpaceInfo newSlotSpace(int x, int y, int row, int column)
	{
		if (this.spaces == null)
			spaces = Lists.newArrayList();
		if (row == 1 && column == 1)
			HelperMod.LOG.warn("Attempting create a single slot space!\n Highly recommond to use newSingletonSlot to " +
					"handle this;");
		SpaceInfoImpl spaceInfo = new SpaceInfoImpl(spaces.size(), x, y, row, column);
		spaces.add(spaceInfo);
		return spaceInfo;
	}

	@Override
	public Inventory.Data buildProperty()
	{
		List<InventoryCommon> temp = Lists.newArrayList();
		InventoryCommon inv;
		if (discrete != null)
		{
			inv = new InventoryCommon("default", this.discrete.size(), null, InventoryCommon.COMMON);
			temp.add(inv);
		}
		for (int i = 0; i < this.spaces.size(); i++)
		{
			SpaceInfoImpl spaceInfo = (SpaceInfoImpl) this.spaces.get(i);
			temp.add(new InventoryCommon(id + "." + i, spaceInfo.count, null, spaceInfo.rule));
		}
		return new Container(ImmutableList.copyOf(temp));
	}

	@Override
	public Inventory.Meta getMeta()
	{
		return this;
	}

	@Override
	public Class<Inventory> getHook()
	{
		return Inventory.class;
	}

	@Override
	public boolean init(Interactive interactive)
	{
		if (interactive instanceof api.simplelib.interactive.inventory.Inventory)
		{
			((api.simplelib.interactive.inventory.Inventory) interactive).provideInventory(this);
			this.id = interactive.getId();
			if (!this.spaces.isEmpty())
				return true;
		}
		return false;
	}

	@Override
	public ImmutableList<SpaceInfo> getSpaces()
	{
		return ImmutableList.copyOf(spaces);
	}

	@Override
	public ImmutableList<SlotInfo> getSlots()
	{
		return ImmutableList.copyOf(discrete);
	}

	public static class Container implements Inventory.Data, IItemHandlerModifiable
	{
		private ImmutableList<InventoryCommon> inventories;

		public Container(ImmutableList<InventoryCommon> inventories)
		{
			this.inventories = inventories;
		}

		@Override
		public void assign(SlotInfo info, Var<ItemStack> holder)
		{
			if (holder instanceof VarItemHolder)
				this.inventories.get(info.parent()).assign(info, (VarItemHolder) holder);
		}

		@Override
		public IInventory getInventory(SlotInfo info)
		{
			return inventories.get(info.parent());
		}

		@Override
		public IInventory getInventory(SpaceInfo info)
		{
			return inventories.get(info.id());
		}

		@Override
		public void readFromNBT(NBTTagCompound tag)
		{
			for (InventoryCommon inventory : inventories)
				inventory.readFromNBT(tag);
		}

		@Override
		public void writeToNBT(NBTTagCompound tag)
		{
			for (InventoryCommon inventory : inventories)
				inventory.writeToNBT(tag);
		}

		@Override
		public void setStackInSlot(int slot, ItemStack stack)
		{

		}

		@Override
		public int getSlots()
		{
			int size = 0;
			for (InventoryCommon inventory : inventories)
				size += inventory.getSizeInventory();
			return size;
		}

		@Override
		public ItemStack getStackInSlot(int slot)
		{
			return null;
		}

		@Override
		public ItemStack insertItem(int slot, ItemStack stack, boolean simulate)
		{
			return null;
		}

		@Override
		public ItemStack extractItem(int slot, int amount, boolean simulate)
		{
			return null;
		}
	}

	public class Info implements SlotInfo
	{
		private int parent;
		protected int x, y, guiSlotSize, id;
		protected InventoryRule rule;

		public Info(int parent, int id, int x, int y)
		{
			this.parent = parent;
			this.id = id;
			this.x = x;
			this.y = y;
		}

		public Info applyRule(InventoryRule rule)
		{
			this.rule = rule;
			return this;
		}

		@Override
		public int parent()
		{
			return parent;
		}

		@Override
		public int id()
		{
			return id;
		}

		@Override
		public SlotInfo setGuiSlotSize(int size)
		{
			this.guiSlotSize = size;
			return this;
		}

		@Override
		public int x()
		{
			return x;
		}

		@Override
		public int y()
		{
			return y;
		}
	}

	public class SpaceInfoImpl implements SpaceInfo
	{
		protected int ySize, xSize, count, id, x, y, guiSlotSize;
		protected InventoryRule rule;

		protected SpaceInfoImpl(int id, int x, int y, int ySize, int xSize)
		{
			this.id = id;
			this.x = x;
			this.y = y;
			this.ySize = ySize;
			this.xSize = xSize;
			this.count = ySize * xSize;
			this.guiSlotSize = 16;
		}

		@Override
		public int id()
		{
			return id;
		}

		@Override
		public SpaceInfo setGuiSlotSize(int size)
		{
			this.guiSlotSize = size;
			return this;
		}

		@Override
		public int x()
		{
			return x;
		}

		@Override
		public int y()
		{
			return y;
		}

		@Override
		public SpaceInfo applyRule(InventoryRule rule)
		{
			this.rule = rule;
			return this;
		}

		@Override
		public SlotInfo get(int x, int y)
		{
			int id = this.xSize * y + x;
			return new Info(this.id, id, this.x + x * this.guiSlotSize, this.y + y * this.guiSlotSize);
		}

		@Override
		public int ySize()
		{
			return ySize;
		}

		@Override
		public int xSize()
		{
			return xSize;
		}

		@Override
		public int count()
		{
			return count;
		}
	}
}
