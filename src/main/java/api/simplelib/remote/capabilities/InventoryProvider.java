package api.simplelib.remote.capabilities;

import api.simplelib.inventory.Inventory;
import api.simplelib.inventory.InventoryBuilder;

/**
 * @author ci010
 */
@CapabilityInjectInterface(Inventory.class)
public interface InventoryProvider
{
	void buildInventory(InventoryBuilder builder);
}
