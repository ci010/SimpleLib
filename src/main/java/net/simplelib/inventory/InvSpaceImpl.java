package net.simplelib.inventory;

import api.simplelib.inventory.Inventory;
import api.simplelib.inventory.InventoryRule;
import api.simplelib.inventory.InventorySpace;
import com.google.common.base.Optional;
import net.minecraft.item.ItemStack;

import java.util.Iterator;

/**
 * @author ci010
 */
public class InvSpaceImpl implements InventorySpace
{
	private int size, offset, xSize, ySize;
	private InventoryRule rule = InventoryRule.COMMON;
	private Inventory parent;
	private String name;

	public InvSpaceImpl(Inventory delegate, int id, int xSize, int ySize)
	{
		this.size = xSize * ySize;
		this.offset = id;
		this.parent = delegate;
	}

	void setName(String name)
	{
		this.name = name;
	}

	@Override
	public ItemStack insertItem(int slot, ItemStack stack, boolean simulate)
	{
		if (slot < size)
			return parent.insertItem(slot + offset, stack, simulate);
		return null;
	}

	@Override
	public ItemStack extractItem(int slot, int amount, boolean simulate)
	{
		if (slot < size)
			return parent.extractItem(slot + offset, amount, simulate);
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
				return current < parent.getSlots();
			}

			@Override
			public ItemStack next()
			{
				return parent.getStackInSlot(current++);
			}

			@Override
			public void remove()
			{
				throw new UnsupportedOperationException();
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
		return parent;
	}

	@Override
	public Optional<String> name()
	{
		return Optional.fromNullable(name);
	}


	@Override
	public int getSlots()
	{
		return size;
	}

	@Override
	public ItemStack getStackInSlot(int slot)
	{
		return parent.getStackInSlot(slot + offset);
	}

	@Override
	public int xSize()
	{
		return xSize;
	}

	@Override
	public int ySize()
	{
		return ySize;
	}
}
