package api.simplelib.nui;

import net.minecraft.entity.player.EntityPlayer;

/**
 * @author ci010
 */
public interface Document
{
	interface Context
	{
		EntityPlayer getOpenedPlayer();

		Object find(String id);
	}
}
