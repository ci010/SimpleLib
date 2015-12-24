package net.simplelib.interactive;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

/**
 * @author ci010
 */
public interface InventoryRule
{
	boolean isUsebleByPlayer(EntityPlayer player);

	boolean isItemValid(ItemStack stack);

	int getInventoryStackLimit();
}
