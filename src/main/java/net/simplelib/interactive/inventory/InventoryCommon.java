package net.simplelib.interactive.inventory;

import api.simplelib.minecraft.inventory.InventoryRule;
import api.simplelib.interactive.inventory.SlotInfo;
import com.google.common.collect.ImmutableList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.simplelib.interactive.process.VarItemHolder;
import api.simplelib.utils.ITagSerializable;

import java.util.List;

/**
 * @author ci010
 */
public class InventoryCommon implements IInventory, ITagSerializable
{
	protected String id;
	protected InventoryRule rule;
	protected ImmutableList<String> namespace;
	protected int size;
	protected List<VarItemHolder> holders;
	protected Listener listener;//TODO handle this

	protected InventoryCommon(String id, int count, List<String> namespace, InventoryRule rule)
	{
		this.id = id;
		this.rule = rule;
		this.size = count;
		this.namespace = ImmutableList.copyOf(namespace);
	}

	public void assign(SlotInfo info, VarItemHolder holder)
	{
		holders.set(info.id(), holder);
	}

	public void applyListener(Listener listener)
	{
		//TODO maybe use list to store multiple listeners.....
		this.listener = listener;
	}

	@Override
	public int getSizeInventory()
	{
		return size;
	}

	@Override
	public ItemStack getStackInSlot(int index)
	{
		return this.holders.get(index).get();
	}

	@Override
	public ItemStack decrStackSize(int index, int count)
	{
		if (this.holders.get(index).get() != null)
		{
			ItemStack stack;
			if (this.holders.get(index).get().stackSize <= size)
			{
				stack = this.holders.get(index).get();
				this.holders.set(index, null);
				this.markDirty();
				return stack;
			}
			else
			{
				stack = this.holders.get(index).get().splitStack(size);
				if (this.holders.get(index).get().stackSize == 0)
					this.holders.get(index).set(null);
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
		if (this.holders.get(index).get() != null)
		{
			ItemStack itemstack = this.holders.get(index).get();
			this.holders.get(index).set(null);
			return itemstack;
		}
		else
			return null;
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack)
	{
		this.holders.get(index).set(stack);
		if (stack != null && stack.stackSize > this.getInventoryStackLimit())
			stack.stackSize = this.getInventoryStackLimit();
		this.markDirty();
	}

	@Override
	public int getInventoryStackLimit()
	{
		return rule.getInventoryStackLimit();
	}

	@Override
	public void markDirty()
	{
		if (listener != null)
			listener.onInventoryChange(this);
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player)
	{
		return rule.isUsebleByPlayer(player);
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
		return rule.isItemValid(stack);
	}

	@Override
	@Deprecated
	public int getField(int id)
	{
		return 0;
	}

	@Override
	@Deprecated
	public void setField(int id, int value) {}

	@Override
	@Deprecated
	public int getFieldCount()
	{
		return 0;
	}

	@Override
	public void clear()
	{
		for (VarItemHolder holder : this.holders)
			holder.set(null);
	}

	@Override
	public String getName()
	{
		return "inventory.".concat(this.id);
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
	public void readFromNBT(NBTTagCompound tag)
	{
		NBTTagList tagList = tag.getTagList(this.getName(), 10);
		for (int i = 0; i < tagList.tagCount(); ++i)
		{
			NBTTagCompound temp = tagList.getCompoundTagAt(i);
			byte slot = temp.getByte("Slot");
			if (slot >= 0 && slot < this.holders.size())
				this.holders.get(slot).set(ItemStack.loadItemStackFromNBT(temp));
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound tag)
	{
		NBTTagList stackList = new NBTTagList();
		for (int i = 0; i < this.holders.size(); ++i)
			if (this.holders.get(i).get() != null)
			{
				NBTTagCompound tagCompound = new NBTTagCompound();
				tagCompound.setByte("Slot", (byte) i);
				this.holders.get(i).get().writeToNBT(tagCompound);
				stackList.appendTag(tagCompound);
			}
		tag.setTag(this.getName(), stackList);
	}

	public interface Listener
	{
		void onInventoryChange(IInventory inventory);
	}
}
