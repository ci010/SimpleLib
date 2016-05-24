package net.simplelib.gui;

import api.simplelib.gui.GuiContainerCommon;
import api.simplelib.gui.GuiScreenCommon;
import api.simplelib.registry.ModHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * @author ci010
 */
@ModHandler
@SideOnly(Side.CLIENT)
public class Hook
{
	@SubscribeEvent
	public void capture(MouseEvent event)
	{
		GuiScreen screen = Minecraft.getMinecraft().currentScreen;
		if (screen instanceof GuiContainerCommon)
			((GuiContainerCommon) screen) .accept(event);
		else if (screen instanceof GuiScreenCommon)
			((GuiScreenCommon) screen).accept(event);
	}
}
