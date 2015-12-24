package net.simplelib.interactive;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

/**
 * @author ci010
 */
public class SlotWeak extends Slot
{
	public SlotWeak(IInventory inventory, int index, int x, int y)
	{
		super(inventory, index, x, y);
	}

	@Override
	public boolean isItemValid(ItemStack stack)
	{
		return inventory.isItemValidForSlot(this.getSlotIndex(), stack);
	}

	@Override
	public boolean canTakeStack(EntityPlayer playerIn)
	{
		return inventory.isUseableByPlayer(playerIn);
	}
}
