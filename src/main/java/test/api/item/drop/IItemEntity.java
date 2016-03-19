package test.api.item.drop;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import test.api.item.ComponentItem;

/**
 * @author ci010
 */
public interface IItemEntity extends ComponentItem
{
	/**
	 * This function should return a new entity to replace the dropped item.
	 * Returning null here will not kill the EntityItem and will leave it to function normally.
	 * Called when the item it placed in a world.
	 *
	 * @param world     The world object
	 * @param location  The EntityItem object, useful for getting the position of the entity
	 * @param itemstack The current item stack
	 * @return A new Entity object to spawn or null
	 */
	Entity createEntity(World world, Entity location, ItemStack itemstack);


}
