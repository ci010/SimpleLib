package api.simplelib.inventory;

import api.simplelib.capabilities.CapabilityBuilder;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nullable;

/**
 * @author ci010
 */
@CapabilityBuilder({Inventory.class, IItemHandler.class})
public interface InventoryBuilder
{
	InventorySpace newSpace(String name, int size, @Nullable EnumFacing... facing);

	InventorySpace newSpace(String name, int size,
							@Nullable InventoryRule rule, @Nullable EnumFacing... facing);

	InventorySlot newSlot(String name, @Nullable EnumFacing... facing);

	InventorySlot newSlot(String name, @Nullable InventoryRule rule, @Nullable EnumFacing... facing);

	Allocator getAllocator();

	interface Allocator
	{
		void allocPos(InventoryElement element, int x, int y);

		void acllocSize(InventorySpace space, int xSize);
	}
}
