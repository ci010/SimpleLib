package api.simplelib;

import api.simplelib.interactive.InteractiveType;
import net.minecraft.entity.player.EntityPlayer;

/**
 * @author ci010
 */
public interface ContextPlayerInteract extends ContextEntity, ContextBlockInteract
{
	EntityPlayer entity();

	InteractiveType type();
}
