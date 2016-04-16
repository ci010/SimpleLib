package test.api.component.item.module;

import test.api.component.entity.livingbase.StatePlayer;
import test.api.component.item.StateItem;
import test.api.world.World;

/**
 * @author ci010
 */
public interface Create
{
	void onCreated(StateItem stack, World worldIn, StatePlayer playerIn);
}
