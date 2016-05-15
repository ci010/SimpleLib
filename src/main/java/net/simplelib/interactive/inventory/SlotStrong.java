package net.simplelib.interactive.inventory;

import api.simplelib.inventory.InventoryRule;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

/**
 * @author ci010
 */
public class SlotStrong extends SlotWeak
{
	private InventoryRule rule;

	public SlotStrong(IInventory inventory, InventoryRule rule, int index, int x, int y)
	{
		super(inventory, index, x, y);
		this.rule = rule;
	}

	@Override
	public boolean isItemValid(ItemStack stack)
	{
		return rule.isItemValid(stack);
	}

	@Override
	public boolean canTakeStack(EntityPlayer playerIn)
	{
		return rule.isUsebleByPlayer(playerIn);
	}
}
