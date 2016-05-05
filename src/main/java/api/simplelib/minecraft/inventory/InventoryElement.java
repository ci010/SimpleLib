package api.simplelib.minecraft.inventory;

import com.google.common.base.Optional;
import net.minecraft.item.ItemStack;

/**
 * @author ci010
 */
public interface InventoryElement extends Iterable<ItemStack>
{
	int id();

	InventoryRule getRule();

	Inventory parent();

	Optional<String> name();
}
