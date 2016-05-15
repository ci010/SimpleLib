package api.simplelib.inventory;

import api.simplelib.Callback;
import net.minecraft.inventory.ISidedInventory;
import net.minecraftforge.items.IItemHandler;

/**
 * @author ci010
 */
public interface Inventory extends IItemHandler, Iterable<InventoryElement>, ISidedInventory
{
	Callback.Container getCallback();

	Layout getLayout();
}
