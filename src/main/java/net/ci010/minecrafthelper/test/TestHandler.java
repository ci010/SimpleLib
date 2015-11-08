package net.ci010.minecrafthelper.test;

import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * @author ci010
 */
public class TestHandler
{
	@SubscribeEvent
	public void onPlayerInteract(PlayerInteractEvent event)
	{
		if (event.action == PlayerInteractEvent.Action.RIGHT_CLICK_AIR)
		{
			System.out.println(event.entityPlayer.worldObj.isRemote ? "client" : "server");
			System.out.println(event.entityPlayer.isRiding());
		}
	}
}
