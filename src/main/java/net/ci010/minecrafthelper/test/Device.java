package net.ci010.minecrafthelper.test;

import com.google.common.collect.Lists;
import net.ci010.minecrafthelper.tileentity.TileEntityWrap;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.World;

import java.util.List;

/**
 * @author ci010
 */
public class Device
{
	private List<GuiComponent> gui = Lists.newArrayList();
	private Container container;
	private BlockContainer block = new BlockContainer(Material.wood)
	{
		@Override
		public TileEntity createNewTileEntity(World worldIn, int meta)
		{
			return tile;
		}
	};

	private TileEntity tile = new TileEntityWrap()
	{
		@Override
		public String getCommandSenderName()
		{
			return null;
		}

		@Override
		public boolean hasCustomName()
		{
			return false;
		}

		@Override
		public IChatComponent getDisplayName()
		{
			return null;
		}

		@Override
		public int getSizeInventory()
		{
			return 0;
		}

		@Override
		public ItemStack getStackInSlot(int index)
		{
			return null;
		}

		@Override
		public ItemStack decrStackSize(int index, int count)
		{
			return null;
		}

		@Override
		public ItemStack getStackInSlotOnClosing(int index)
		{
			return null;
		}

		@Override
		public void setInventorySlotContents(int index, ItemStack stack)
		{

		}

		@Override
		public int getInventoryStackLimit()
		{
			return 0;
		}

		@Override
		public boolean isUseableByPlayer(EntityPlayer player)
		{
			return false;
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
			return false;
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
	};

	private GuiContainer guiContainer = new GuiContainer(container)
	{
		@Override
		protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
		{
			for (GuiComponent guiComponent : gui)
				guiComponent.draw();
		}
	};

	public Device setBackground()
	{
		return this;
	}

	public Device addString(String s, int x, int y)
	{
		return this;
	}

	public Device addSlot(Slot slot)
	{
		gui.add(new ModSlot(slot));
		return this;
	}

	public Device addBar(ModBar bar)
	{
		gui.add(bar);
		return this;
	}
}
