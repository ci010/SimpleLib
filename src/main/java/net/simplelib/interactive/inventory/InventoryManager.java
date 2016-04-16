package net.simplelib.interactive.inventory;

import api.simplelib.Context;
import api.simplelib.Var;
import api.simplelib.interactive.Interactive;
import api.simplelib.interactive.inventory.Inventory;
import api.simplelib.minecraft.inventory.InventoryRule;
import api.simplelib.interactive.inventory.SlotInfo;
import api.simplelib.interactive.inventory.SpaceInfo;
import api.simplelib.interactive.meta.InteractiveProperty;
import api.simplelib.interactive.meta.ModInteractiveMeta;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.simplelib.HelperMod;
import net.simplelib.interactive.process.VarItemHolder;
import api.simplelib.utils.ITagSerializable;

import java.util.List;
import java.util.concurrent.Callable;

/**
 * @author ci010
 */
@ModInteractiveMeta
public class InventoryManager implements InteractiveProperty,
										 Callable<Inventory.InvData>
{
	private class WorkerManager implements Inventory.Manager, Worker
	{
		protected List<SpaceInfo> spaces;
		protected List<SlotInfo> discrete;
		private String id;

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
		public boolean init(Interactive interactive)
		{
			if (interactive instanceof Inventory)
				((Inventory) interactive).provideInventory(this);
			if (this.spaces.isEmpty() && this.discrete.isEmpty())
				return false;
			this.id = interactive.getId();
			return true;
		}

		@Override
		public ITagSerializable buildProperty(Context context)
		{
			List<InventoryCommon> temp = Lists.newArrayList();
			InventoryCommon inv;
			if (discrete != null)
			{
				inv = new InventoryCommon("default", discrete.size(), null, InventoryRule.COMMON);
				temp.add(inv);
			}
			for (int i = 0; i < spaces.size(); i++)
			{
				SpaceInfoImpl spaceInfo = (SpaceInfoImpl) spaces.get(i);
				temp.add(new InventoryCommon(id + "." + i, spaceInfo.count, null, spaceInfo.rule));
			}
			return new Container(this, ImmutableList.copyOf(temp));
		}
	}



	@Override
	public Worker newWorker()
	{
		return new WorkerManager();
	}

	@Override
	public void build()
	{
		CapabilityManager.INSTANCE.register(Inventory.InvData.class, new Capability.IStorage<Inventory.InvData>()
		{
			@Override
			public NBTBase writeNBT(Capability<Inventory.InvData> capability, Inventory.InvData instance, EnumFacing side)
			{
				NBTTagCompound tag = new NBTTagCompound();
				instance.writeToNBT(tag);
				return tag;
			}

			@Override
			public void readNBT(Capability<Inventory.InvData> capability, Inventory.InvData instance, EnumFacing side, NBTBase nbt)
			{
				if (nbt instanceof NBTTagCompound)
					instance.readFromNBT((NBTTagCompound) nbt);
			}
		}, this);
	}

	@Override
	public Class<? extends Interactive> interfaceType()
	{
		return Inventory.class;
	}

	@Override
	public Inventory.InvData call() throws Exception
	{
		return null;
	}

	public class Container implements Inventory.InvData, IItemHandlerModifiable
	{
		private ImmutableList<InventoryCommon> inventories;
		private WorkerManager manager;

		public Container(WorkerManager manager, ImmutableList<InventoryCommon> inventories)
		{
			this.manager = manager;
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
		public ImmutableList<SpaceInfo> getSpacesInfo()
		{
			return ImmutableList.copyOf(manager.spaces);
		}

		@Override
		public ImmutableList<SlotInfo> getSlotsInfo()
		{
			return ImmutableList.copyOf(manager.discrete);
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
