package test.api.component.block.module;

import api.simplelib.ContextBlockInteract;
import test.api.component.block.StateBlock;
import test.api.component.entity.livingbase.StatePlayer;

/**
 * @author ci010
 */
public interface RightClick
{
	boolean onBlockActivated(StateBlock state, StatePlayer playerIn, ContextBlockInteract info);
}
