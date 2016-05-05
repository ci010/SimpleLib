package api.simplelib.minecraft.inventory;

import api.simplelib.common.Nullable;
import net.minecraft.util.EnumFacing;

/**
 * @author ci010
 */
public interface InventoryBuilder
{
	InventorySpace newSpace(int size, @Nullable EnumFacing facing, InventoryRule rule);

	InventorySpace newSpace(int size, @Nullable EnumFacing facing);

	InventorySlot newSlot(@Nullable EnumFacing facing);

	InventorySlot newSlot(@Nullable EnumFacing facing, InventoryRule rule);

	InventoryBuilder allocName(InventoryElement element, String name);

	InventoryBuilder allocLength(InventorySpace space, int length);

	InventoryBuilder allocPos(InventoryElement element, int x, int y);

	int currentSize();

	InventoryElement getElement(int i);
}
