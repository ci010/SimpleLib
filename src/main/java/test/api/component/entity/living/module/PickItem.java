package test.api.component.entity.living.module;

import test.api.component.entity.StateEntity;

/**
 * @author ci010
 */
public interface PickItem
{
	void onItemPickup(StateEntity entity, int stackSize);
}
