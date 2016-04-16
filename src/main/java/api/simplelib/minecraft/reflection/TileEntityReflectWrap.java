package api.simplelib.minecraft.reflection;

import com.google.common.collect.Lists;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;

/**
 * @author ci010
 */

public abstract class TileEntityReflectWrap extends TileEntity implements IInventory
{
	private ItemStack stack[];
	public final Field[] watching;
	private String id;

	public TileEntityReflectWrap()
	{
		super();
		List<Field> list = Lists.newArrayList();
		for (Field f : this.getClass().getFields())
			if (!Modifier.isStatic(f.getModifiers()) && f.getType() == Integer.class &&
					f.isAnnotationPresent(Watching.class))
				list.add(f);
		this.id = this.getClass().getName();
		this.watching = (Field[]) list.toArray();
		this.stack = new ItemStack[this.stackSize()];
	}

	protected abstract int stackSize();

	@Override
	public String getName()
	{
		return id;
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
	public int getSizeInventory()
	{
		return stack.length;
	}

	@Override
	public ItemStack getStackInSlot(int num)
	{
		return stack[num];
	}

	@Override
	public ItemStack decrStackSize(int index, int size)
	{
		if (this.stack[index] != null)
		{
			ItemStack stack;
			if (this.stack[index].stackSize <= size)
			{
				stack = this.stack[index];
				this.stack[index] = null;
				return stack;
			}
			else
			{
				stack = this.stack[index].splitStack(size);
				if (this.stack[index].stackSize == 0)
					this.stack[index] = null;
				return stack;
			}
		}
		else
			return null;
	}

	@Override
	public ItemStack removeStackFromSlot(int index)
	{
		if (this.stack[index] != null)
		{
			ItemStack itemstack = this.stack[index];
			this.stack[index] = null;
			return itemstack;
		}
		else
			return null;
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack stack)
	{
		this.stack[i] = stack;
		if (stack != null && stack.stackSize > this.getInventoryStackLimit())
			stack.stackSize = this.getInventoryStackLimit();
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
	public void clear()
	{
		for (int i = 0; i < this.stack.length; ++i)
			this.stack[i] = null;
	}

	@Override
	public void readFromNBT(NBTTagCompound tag)
	{
		super.readFromNBT(tag);
		NBTTagList tagList = tag.getTagList(this.getName(), 10);
		this.stack = new ItemStack[this.getSizeInventory()];
		for (int i = 0; i < tagList.tagCount(); ++i)
		{
			NBTTagCompound temp = tagList.getCompoundTagAt(i);
			byte slot = temp.getByte("Slot");
			if (slot >= 0 && slot < this.stack.length)
				this.stack[slot] = ItemStack.loadItemStackFromNBT(temp);
		}
		for (Field f : watching)
			try
			{
				f.setInt(this, tag.getInteger(f.getName()));
			}
			catch (IllegalArgumentException e)
			{
				e.printStackTrace();
			}
			catch (IllegalAccessException e)
			{
				e.printStackTrace();
			}
	}


	@Override
	public void writeToNBT(NBTTagCompound tag)
	{
		super.writeToNBT(tag);
		for (Field f : watching)
			try
			{
				tag.setInteger(f.getName(), f.getInt(this));
			}
			catch (IllegalArgumentException e)
			{
				e.printStackTrace();
			}
			catch (IllegalAccessException e)
			{
				e.printStackTrace();
			}

		NBTTagList tagList = new NBTTagList();
		for (int i = 0; i < this.stack.length; ++i)
			if (this.stack[i] != null)
			{
				NBTTagCompound tagCompound = new NBTTagCompound();
				tagCompound.setByte("Slot", (byte) i);
				this.stack[i].writeToNBT(tagCompound);
				tagList.appendTag(tagCompound);
			}
		tag.setTag(this.getName(), tagList);
	}
}
