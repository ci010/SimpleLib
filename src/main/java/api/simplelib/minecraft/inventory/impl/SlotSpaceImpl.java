package api.simplelib.minecraft.inventory.impl;

import api.simplelib.minecraft.Callback;
import api.simplelib.minecraft.inventory.Inventory;
import api.simplelib.minecraft.inventory.InventoryRule;
import api.simplelib.minecraft.inventory.InventorySlot;
import net.minecraft.item.ItemStack;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * @author ci010
 */
public class SlotSpaceImpl implements Callback.Container<InventorySlot>, InventorySlot
{
	private InventoryRule rule;
	private LinkedList<Callback<InventorySlot>> callBacks = new LinkedList<Callback<InventorySlot>>();
	private Inventory parent;
	private int index;

	public SlotSpaceImpl(Inventory parent, int index)
	{
		this.parent = parent;
		this.index = index;
	}

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
	public int id()
	{
		return index;
	}

	public void setRule(InventoryRule rule)
	{
		this.rule = rule;
	}

	@Override
	public Callback.Container<InventorySlot> callbackContainer()
	{
		return this;
	}

	@Override
	public ItemStack getStackInSlot()
	{
		return parent.getStackInSlot(index);
	}

	@Override
	public ItemStack insertItem(ItemStack stack, boolean simulate)
	{
		return parent.insertItem(index, stack, simulate);
	}

	@Override
	public ItemStack extractItem(int amount, boolean simulate)
	{
		return parent.extractItem(index, amount, simulate);
	}

	@Override
	public void add(Callback<InventorySlot> callBack)
	{
		callBacks.add(callBack);
	}

	@Override
	public void remove(Callback<InventorySlot> callBack)
	{
		callBacks.remove(callBack);
	}

	@Override
	public Iterator<Callback<InventorySlot>> iterator()
	{
		return this.callBacks.iterator();
	}
}
