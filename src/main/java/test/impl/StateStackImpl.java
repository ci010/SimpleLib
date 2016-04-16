package test.impl;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import test.api.component.item.ComponentItem;
import test.api.component.item.StateItem;

import java.lang.ref.WeakReference;

/**
 * @author ci010
 */
public class StateStackImpl implements StateItem
{
	private WeakReference<ItemStack> stack;
	private ComponentItem type;
	private WorldImpl pool;

	StateStackImpl(WorldImpl pool)
	{
		this.pool = pool;
	}

	StateItem with(ItemStack stack)
	{
		this.stack = new WeakReference<ItemStack>(stack);
		this.type = GameComponents.get(stack.getItem());
		return this;
	}

	@Override
	public ComponentItem getType()
	{
		return type;
	}

	private ItemStack getStack()
	{
		ItemStack itemStack = stack.get();
		if (itemStack == null)
			pool.recycle(this);
		return itemStack;
	}

	@Override
	public boolean isStackable()
	{
		return getStack().isStackable();
	}

	@Override
	public int getStackSize()
	{
		return getStack().stackSize;
	}

	@Override
	public void setStackSize(int size)
	{
		getStack().stackSize = size;
	}

	@Override
	public int getMeta()
	{
		return getStack().getItemDamage();
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing)
	{
		return getStack().hasCapability(capability, facing);
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing)
	{
		return getStack().getCapability(capability, facing);
	}

	@Override
	public NBTTagCompound serializeNBT()
	{
		return getStack().serializeNBT();
	}

	@Override
	public void deserializeNBT(NBTTagCompound nbt)
	{
		getStack().deserializeNBT(nbt);
	}
}
