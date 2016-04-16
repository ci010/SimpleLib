package test.api.inventory;

import test.api.component.item.StateItem;

/**
 * @author ci010
 */
public interface InventorySlot extends Element
{
	StateItem insertItem(StateItem stateItem);

	StateItem extractItem(int amount);

	boolean isSelected();
}
