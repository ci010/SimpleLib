package api.simplelib.interactive.inventory;

/**
 * @author ci010
 */
public interface SlotInfo
{
	/**
	 * @return The id of this slot. Used by save/load.
	 */
	String id();

	/**
	 * Still WIP
	 *
	 * @param size
	 * @return
	 */
	@Deprecated
	SlotInfo setGuiSlotSize(int size);

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
	SlotInfo applyRule(InventoryRule rule);
}
