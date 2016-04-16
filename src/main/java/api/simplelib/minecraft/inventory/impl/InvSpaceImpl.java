package api.simplelib.minecraft.inventory.impl;

import api.simplelib.minecraft.Callback;
import api.simplelib.minecraft.inventory.Inventory;
import api.simplelib.minecraft.inventory.InventoryRule;
import api.simplelib.minecraft.inventory.InventorySpace;
import com.google.common.collect.Lists;
import net.minecraft.item.ItemStack;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * @author ci010
 */
public class InvSpaceImpl implements InventorySpace
{
	private int size, offset;
	private InventoryRule rule = InventoryRule.COMMON;
	private Inventory delegate;
	private Callback.Container<InventorySpace> container = new Callback.Container<InventorySpace>()
	{
		private LinkedList<Callback<InventorySpace>> spaces = Lists.newLinkedList();

		@Override
		public void add(Callback<InventorySpace> callBack)
		{
			spaces.add(callBack);
		}

		@Override
		public void remove(Callback<InventorySpace> callBack)
		{
			spaces.remove(callBack);
		}

		@Override
		public Iterator<Callback<InventorySpace>> iterator()
		{
			return spaces.iterator();
		}
	};

	public InvSpaceImpl(Inventory delegate, int id, int size)
	{
		this.size = size;
		this.offset = id;
		this.delegate = delegate;
	}

	private void markDirty()
	{
		for (Callback<InventorySpace> callBack : container)
			callBack.onChange(this);
	}

	@Override
	public ItemStack insertItem(int slot, ItemStack stack, boolean simulate)
	{
		if (slot < size)
			return delegate.insertItem(slot + offset, stack, simulate);
		return null;
	}

	@Override
	public ItemStack extractItem(int slot, int amount, boolean simulate)
	{
		if (slot < size)
			return delegate.extractItem(slot + offset, amount, simulate);
		return null;
	}

	@Override
	public Iterator<ItemStack> iterator()
	{
		return new Iterator<ItemStack>()
		{
			int current = 0;

			@Override
			public boolean hasNext()
			{
				return current < delegate.getSlots();
			}

			@Override
			public ItemStack next()
			{
				return delegate.getStackInSlot(current++);
			}

			@Override
			public void remove()
			{

			}
		};
	}

	@Override
	public int id()
	{
		return offset;
	}

	void setRule(InventoryRule rule)
	{
		this.rule = rule;
	}

	@Override
	public InventoryRule getRule()
	{
		return rule;
	}

	@Override
	public Inventory parent()
	{
		return delegate;
	}

	@Override
	public Callback.Container<InventorySpace> callbackContainer()
	{
		return container;
	}

	@Override
	public int getSlots()
	{
		return size;
	}

	@Override
	public ItemStack getStackInSlot(int slot)
	{
		return delegate.getStackInSlot(slot + offset);
	}

}
