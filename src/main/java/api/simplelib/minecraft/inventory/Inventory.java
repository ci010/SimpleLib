package api.simplelib.minecraft.inventory;

import api.simplelib.minecraft.Callback;
import net.minecraft.inventory.ISidedInventory;
import net.minecraftforge.items.IItemHandler;

/**
 * @author ci010
 */
public interface Inventory extends IItemHandler, Iterable<InventoryElement>, ISidedInventory
{
	Callback.Container getCallback();

	void setLayout(Layout layout);

	Layout getLayout();
}
