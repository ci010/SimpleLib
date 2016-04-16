package api.simplelib.minecraft.inventory;

import api.simplelib.minecraft.Callback;

/**
 * @author ci010
 */
public interface InventoryElement<T extends InventoryElement<T>>
{
	int id();

	InventoryRule getRule();

	Callback.Container<T> callbackContainer();

	Inventory parent();
}
