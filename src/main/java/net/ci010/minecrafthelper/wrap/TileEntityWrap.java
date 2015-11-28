package net.ci010.minecrafthelper.wrap;

import net.ci010.minecrafthelper.test.VarInteger;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.server.gui.IUpdatePlayerListBox;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;

/**
 * @author ci010
 */
public class TileEntityWrap extends TileEntity implements IInventory, IUpdatePlayerListBox
{
	ItemStack[] stacks;
	VarInteger[] integers;
	MachineProcess[] process;

	private String name;

	public TileEntityWrap load(String name, ItemStack[] stacks, VarInteger[] integers, MachineProcess[] process)
	{
		this.stacks = stacks;
		this.process = process;
		this.integers = integers;
		this.name = name;
		return this;
	}

	@Override
	public boolean hasCustomName()
	{
		return false;
	}

	@Override
	public void update()
	{
		for (MachineProcess proces : process)
			proces.preUpdate();
		for (MachineProcess proces : process)
			if (proces.shouldUpdate())
				proces.update();
		for (MachineProcess proces : process)
			proces.postUpdate();
	}

	@Override
	public String getCommandSenderName()
	{
		return name;
	}

	@Override
	public IChatComponent getDisplayName()
	{
		return new ChatComponentText(this.getCommandSenderName());
	}

	@Override
	public ItemStack getStackInSlot(int num)
	{
		return stacks[num];
	}

	@Override
	public int getSizeInventory()
	{
		return stacks.length;
	}

	@Override
	public ItemStack decrStackSize(int index, int size)
	{
		if (this.stacks[index] != null)
		{
			ItemStack stack;
			if (this.stacks[index].stackSize <= size)
			{
				stack = this.stacks[index];
				this.stacks[index] = null;
				return stack;
			}
			else
			{
				stack = this.stacks[index].splitStack(size);
				if (this.stacks[index].stackSize == 0)
					this.stacks[index] = null;
				return stack;
			}
		}
		else
			return null;
	}

	@Override
	public void clear()
	{
		for (int i = 0; i < this.stacks.length; ++i)
			this.stacks[i] = null;
	}

	@Override
	public int getInventoryStackLimit()
	{
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player)
	{
		return true;
	}

	@Override
	public void openInventory(EntityPlayer player)
	{}

	@Override
	public void closeInventory(EntityPlayer player)
	{}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack)
	{
		return false;
	}

	@Override
	@Deprecated
	public final int getField(int index)
	{
		return -1;
	}

	@Override
	@Deprecated
	public final void setField(int index, int value)
	{}

	@Override
	@Deprecated
	public final int getFieldCount()
	{
		return -1;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int par1)
	{
		if (this.stacks[par1] != null)
		{
			ItemStack itemstack = this.stacks[par1];
			this.stacks[par1] = null;
			return itemstack;
		}
		else
			return null;
	}

	@Override
	public void setInventorySlotContents(int var1, ItemStack var2)
	{
		this.stacks[var1] = var2;
		if (var2 != null && var2.stackSize > this.getInventoryStackLimit())
			var2.stackSize = this.getInventoryStackLimit();
	}

	@Override
	public void readFromNBT(NBTTagCompound tag)
	{
		super.readFromNBT(tag);
		this.name = tag.getString("machine_name");
		NBTTagList tagList = tag.getTagList(this.getCommandSenderName(), 10);
		this.stacks = new ItemStack[this.getSizeInventory()];
		for (int i = 0; i < tagList.tagCount(); ++i)
		{
			NBTTagCompound temp = tagList.getCompoundTagAt(i);
			byte slot = temp.getByte("Slot");
			if (slot >= 0 && slot < this.stacks.length)
				this.stacks[slot] = ItemStack.loadItemStackFromNBT(temp);
		}
		int[] ints = tag.getIntArray("VarInt");
		this.integers = new VarInteger[ints.length];
		for (int i = 0; i < ints.length; ++i)
			integers[i] = new VarInteger(ints[i]);
		Machine.linkTileEntityProcess(this);
	}


	@Override
	public void writeToNBT(NBTTagCompound tag)
	{
		super.writeToNBT(tag);
		tag.setString("machine_name", this.name);

		int[] arr = new int[this.integers.length];
		for (int i = 0; i < this.integers.length; ++i)
			arr[i] = integers[i].getData();
		tag.setIntArray("VarInt", arr);

		NBTTagList stackList = new NBTTagList();
		for (int i = 0; i < this.stacks.length; ++i)
			if (this.stacks[i] != null)
			{
				NBTTagCompound tagCompound = new NBTTagCompound();
				tagCompound.setByte("Slot", (byte) i);
				this.stacks[i].writeToNBT(tagCompound);
				stackList.appendTag(tagCompound);
			}
		tag.setTag(this.getCommandSenderName(), stackList);
	}
}
