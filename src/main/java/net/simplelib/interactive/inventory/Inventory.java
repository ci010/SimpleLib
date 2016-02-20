package net.simplelib.interactive.inventory;

import api.simplelib.interactive.inventory.InventoryRule;
import com.google.common.collect.ImmutableList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.simplelib.common.nbt.ITagSerial;
import net.simplelib.interactive.process.VarItemHolder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author ci010
 */
public class Inventory implements IInventory, ITagSerial
{
	public static final InventoryRule COMMON = new InventoryRule()
	{
		@Override
		public boolean isUsebleByPlayer(EntityPlayer player)
		{
			return true;
		}

		@Override
		public boolean isItemValid(ItemStack stack)
		{
			return true;
		}

		@Override
		public int getInventoryStackLimit()
		{
			return 64;
		}
	};

	protected String id;
	protected InventoryRule rule;
	protected ImmutableList<String> namespace;
	protected int size;
	protected List<VarItemHolder> holders;
	protected Listener listener;//TODO handle this

	protected Inventory(String id, int size, String[] namespace, InventoryRule rule)
	{
		this.id = id;
		this.rule = rule;
		this.namespace = ImmutableList.copyOf(namespace);
		this.size = size;
		this.holders = new ArrayList<VarItemHolder>(size);
		Collections.fill(holders, new VarItemHolder());
	}

	public Inventory assignNamespace(String name, VarItemHolder holder)
	{
		if (namespace.contains(name))
			holders.set(namespace.indexOf(name), holder);
		else
			throw new IllegalArgumentException("The slot name not matched!");
		return this;
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
	public ItemStack getStackInSlotOnClosing(int index)
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
	public String getCommandSenderName()
	{
		return this.id.concat("_inventory");
	}

	@Override
	public boolean hasCustomName()
	{
		return false;
	}

	@Override
	public IChatComponent getDisplayName()
	{
		return new ChatComponentText(this.getCommandSenderName());
	}

	@Override
	public void readFromNBT(NBTTagCompound tag)
	{
		NBTTagList tagList = tag.getTagList(this.getCommandSenderName(), 10);
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
		tag.setTag(this.getCommandSenderName(), stackList);
	}

	public interface Listener
	{
		void onInventoryChange(IInventory inventory);
	}
}
