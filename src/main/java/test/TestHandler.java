package test;

import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.simplelib.HelperMod;
import net.simplelib.annotation.type.Handler;

/**
 * @author ci010
 */
@Handler
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

	static int counter = 0;

//	@SubscribeEvent
	public void onTick(TickEvent.ClientTickEvent event)
	{
		if (++counter > 20)
		{
			counter = 0;
			HelperMod.proxy.isSinglePlayer();
		}
	}
}
