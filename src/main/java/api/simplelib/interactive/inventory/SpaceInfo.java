package api.simplelib.interactive.inventory;

import api.simplelib.minecraft.inventory.InventoryRule;

/**
 * @author ci010
 */
public interface SpaceInfo
{
	/**
	 * @return The id of this slot. Used by save/load.
	 */
	int id();

	/**
	 * Still WIP
	 *
	 * @param size
	 * @return
	 */
	@Deprecated
	SpaceInfo setGuiSlotSize(int size);

	/**
	 * @return The default X position on screen of this slot.
	 */
	int x();

	/**
	 * @return The default Y position on screen of this slot.
	 */
	int y();

	/**
	 * Apply a new {@link InventoryRule} to this slot.
	 *
	 * @param rule The new inventory rule.
	 * @return this
	 * @see InventoryRule
	 */
	SpaceInfo applyRule(InventoryRule rule);

	SlotInfo get(int x, int y);

	int ySize();

	int xSize();

	int count();
}
