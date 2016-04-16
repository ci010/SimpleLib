package api.simplelib.minecraft.inventory;

import net.minecraft.util.EnumFacing;

/**
 * @author ci010
 */
public interface InventoryBuilder
{
	Inventory buildInventory();

	InventorySpace newSpace(int size, EnumFacing facing, InventoryRule rule);

	InventorySpace newSpace(int size, EnumFacing facing);

	InventorySlot newSlot(EnumFacing facing);

	InventorySlot newSlot(EnumFacing facing, InventoryRule rule);
}
