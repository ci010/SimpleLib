package api.simplelib.minecraft.inventory.impl;

import api.simplelib.minecraft.Callback;
import api.simplelib.minecraft.inventory.InventoryElement;
import api.simplelib.minecraft.inventory.Inventory;
import api.simplelib.minecraft.inventory.Layout;
import com.google.common.collect.Lists;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IChatComponent;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * @author ci010
 */
public class InvImpl implements Inventory
{
	private ArrayList<ItemStack> stacks;
	private EnumMap<EnumFacing, int[]> sideMap;
	private ArrayList<InventoryElement> elements;
	private int size;
	private Layout layout;
	private IItemHandler wrap;
	private Callback.Container<ISidedInventory> container = new Callback.Container<ISidedInventory>()
	{
		private LinkedList<Callback<ISidedInventory>> spaces = Lists.newLinkedList();

		@Override
		public void add(Callback<ISidedInventory> callBack)
		{
			spaces.add(callBack);
		}

		@Override
		public void remove(Callback<ISidedInventory> callBack)
		{
			spaces.remove(callBack);
		}

		@Override
		public Iterator<Callback<ISidedInventory>> iterator()
		{
			return spaces.iterator();
		}
	};

	public InvImpl()
	{
		this.wrap = new InvWrapper(this);
	}

	void build(EnumMap<EnumFacing, int[]> sideMap, int size, ArrayList<InventoryElement> elements)
	{
		this.sideMap = sideMap;
		this.size = size;
		this.elements = elements;
		this.stacks = Lists.newArrayListWithCapacity(this.size);
	}

	@Override
	public int[] getSlotsForFace(EnumFacing side)
	{
		return sideMap != null ? sideMap.get(side) : new int[0];
	}

	@Override
	public int getSizeInventory()
	{
		return size;
	}

	@Override
	public int getSlots()
	{
		return 0;
	}

	@Override
	public ItemStack getStackInSlot(int index)
	{
		return stacks.get(index);
	}

	@Override
	public ItemStack insertItem(int slot, ItemStack stack, boolean simulate)
	{
		return wrap.insertItem(slot, stack, simulate);
	}

	@Override
	public ItemStack extractItem(int slot, int amount, boolean simulate)
	{
		return wrap.extractItem(slot, amount, simulate);
	}

	@Override
	public ItemStack decrStackSize(int index, int count)
	{
		if (this.stacks.get(index) != null)
		{
			ItemStack stack;
			if (this.stacks.get(index).stackSize <= size)
			{
				stack = this.stacks.get(index);
				this.stacks.set(index, null);
				this.markDirty();
				return stack;
			}
			else
			{
				stack = this.stacks.get(index).splitStack(size);
				if (this.stacks.get(index).stackSize == 0)
					this.stacks.set(index, null);
				this.markDirty();
				return stack;
			}
		}
		else
			return null;
	}

	@Override
	public ItemStack removeStackFromSlot(int index)
	{
		if (this.stacks.get(index) != null)
		{
			ItemStack itemstack = this.stacks.get(index);
			this.stacks.set(index, null);
			return itemstack;
		}
		else
			return null;
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack)
	{
		this.stacks.set(index, stack);
		if (stack != null && stack.stackSize > this.getInventoryStackLimit())
			stack.stackSize = this.getInventoryStackLimit();
		this.markDirty();
	}

	@Override
	public int getInventoryStackLimit()
	{
		return 64;
	}

	@Override
	public void markDirty()
	{
		for (Callback<ISidedInventory> callBack : this.getCallback())
			callBack.onChange(this);
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player)
	{
		return true;
	}

	@Override
	public void openInventory(EntityPlayer player)
	{

	}

	@Override
	public void closeInventory(EntityPlayer player)
	{

	}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack)
	{
		return elements.get(index).getRule().isItemValid(stack);
	}

	@Override
	public int getField(int id)
	{
		return 0;
	}

	@Override
	public void setField(int id, int value)
	{

	}

	@Override
	public int getFieldCount()
	{
		return 0;
	}

	@Override
	public void clear()
	{

	}

	@Override
	public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction)
	{
		if (sideMap.containsKey(direction))
			for (int i : sideMap.get(direction))
				if (index == i)
					return this.elements.get(index).getRule().isItemValid(itemStackIn);
		return false;
	}

	@Override
	public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction)
	{
		if (sideMap.containsKey(direction))
			for (int i : sideMap.get(direction))
				if (index == i)
					return this.elements.get(index).getRule().isItemValid(stack);
		return false;
	}

	@Override
	public String getName()
	{
		return "Inventory_Impl";
	}

	@Override
	public boolean hasCustomName()
	{
		return false;
	}

	@Override
	public IChatComponent getDisplayName()
	{
		return new ChatComponentText(this.getName());
	}

	@Override
	public Callback.Container<ISidedInventory> getCallback()
	{
		return this.container;
	}

	@Override
	public void setLayout(Layout layout)
	{
		this.layout = layout;
	}

	@Override
	public Layout getLayout()
	{
		return layout;
	}

	@Override
	public Iterator<InventoryElement> iterator()
	{
		return null;
	}
}
