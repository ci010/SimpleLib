package test.api.component.item.module.drop;

import test.api.component.entity.StateEntity;
import test.api.component.item.StateItem;
import test.api.world.World;

/**
 * @author ci010
 */
public interface IItemEntity
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
	StateEntity createEntity(World world, StateEntity location, StateItem itemstack);
}
