package net.ci010.minecrafthelper.container;

import com.google.common.collect.Lists;
import net.ci010.minecrafthelper.tileentity.TileEntityReflectWrap;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.lang.reflect.Field;
import java.util.List;

/**
 * @author ci010
 */

public class ContainerReflectWrap extends Container
{
	protected List<Integer> paramList = Lists.newArrayList();
	protected TileEntityReflectWrap tile;
	protected int paraSize;

	public ContainerReflectWrap(TileEntityReflectWrap tile, InventoryPlayer inv)
	{
		this.tile = tile;
		int index;
		for (index = 0; index < 3; ++index)
			for (int offset = 0; offset < 9; ++offset)
				this.addSlotToContainer(new Slot(inv, offset + index * 9 + 9, 8 + offset * 18, 84 + index * 18));
		for (index = 0; index < 9; ++index)
			this.addSlotToContainer(new Slot(inv, index, 8 + index * 18, 142));
	}

	@Override
	public boolean canInteractWith(EntityPlayer playerIn)
	{
		return true;
	}

	public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2)
	{
		return null;
	}

	@Override
	public void onCraftGuiOpened(ICrafting iCrafting)
	{
		super.onCraftGuiOpened(iCrafting);
		int count = 0;
		for (Field f : tile.watching)
			try
			{
				iCrafting.sendProgressBarUpdate(this, count++, (Integer) f.get(tile));
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
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int id, int value)
	{
		if (id < paraSize)
			try
			{
				tile.watching[id].set(tile, value);
			}
			catch (IllegalArgumentException e)
			{
				e.printStackTrace();
			}
			catch (IllegalAccessException e)
			{
				e.printStackTrace();
			}
			catch (SecurityException e)
			{
				e.printStackTrace();
			}
	}

	@Override
	public void detectAndSendChanges()
	{
		super.detectAndSendChanges();
		for (int i = 0; i < this.crafters.size(); ++i)
		{
			ICrafting icrafting = (ICrafting) this.crafters.get(i);

			int count = 0;
			for (Field f : tile.watching)
			{
				try
				{
					int tileV = (Integer) f.get(tile);
					if (this.paramList.get(count) != tileV)
						icrafting.sendProgressBarUpdate(this, count, tileV);
					this.paramList.set(count++, tileV);
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
		}
	}
}