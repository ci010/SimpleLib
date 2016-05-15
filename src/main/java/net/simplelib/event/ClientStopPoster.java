package net.simplelib.event;

import net.minecraft.client.gui.GuiMainMenu;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import api.simplelib.registry.ModHandler;

/**
 * @author ci010
 */
@ModHandler
public class ClientStopPoster
{
	@SubscribeEvent
	public void onStop(GuiScreenEvent.ActionPerformedEvent event)
	{
		if (event.gui instanceof GuiMainMenu && event.button.id == 4)
			MinecraftForge.EVENT_BUS.post(new ClientStopEvent());
	}
}
