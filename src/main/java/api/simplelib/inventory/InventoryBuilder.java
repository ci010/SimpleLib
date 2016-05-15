package api.simplelib.inventory;

import api.simplelib.utils.Nullable;
import net.minecraft.util.EnumFacing;

/**
 * @author ci010
 */

public interface InventoryBuilder
{
	InventorySpace newSpace(int xSize, int ySize, @Nullable EnumFacing facing, InventoryRule rule);

	InventorySpace newSpace(int xSize, int ySize, @Nullable EnumFacing facing);

	InventorySlot newSlot(@Nullable EnumFacing facing);

	InventorySlot newSlot(@Nullable EnumFacing facing, InventoryRule rule);

	InventoryBuilder allocName(InventoryElement element, String name);

	InventoryBuilder allocPos(InventoryElement element, int x, int y);

	int currentSize();

	InventoryElement getElement(int i);
}
