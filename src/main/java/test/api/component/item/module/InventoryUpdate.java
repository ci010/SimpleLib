package test.api.component.item.module;

import test.api.component.entity.livingbase.StatePlayer;
import test.api.component.item.StateItem;
import test.api.inventory.InventorySlot;
import test.api.world.World;

/**
 * @author ci010
 */
public interface InventoryUpdate
{
	/**
	 * Called each tick as long the item is on a player inventory. Uses by maps to check if is on a player hand and
	 * update it's contents.
	 */
	void onUpdate(StateItem stack, World worldIn, StatePlayer player, InventorySlot itemSlot);
}
