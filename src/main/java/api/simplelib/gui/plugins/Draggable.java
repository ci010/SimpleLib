package api.simplelib.gui.plugins;

import api.simplelib.gui.components.GuiComponent;
import api.simplelib.gui.event.DragEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * @author ci010
 */
public class Draggable implements Plugin
{
	private GuiComponent gui;
	private int adjustX, adjustY;

	@SubscribeEvent
	public void onDrag(DragEvent event)
	{
		if (event.getComponent() != gui)
			return;
		if (event.getDraggingTime() <= 100)
		{
			adjustX = event.getMouseX() - gui.getX();
			adjustY = event.getMouseY() - gui.getY();
		}
		gui.setPos(gui.getX() - adjustX, gui.getY() - adjustY);
	}

	@Override
	public void plugin(GuiComponent component)
	{
		this.gui = component;
		MinecraftForge.EVENT_BUS.register(this);
	}

	@Override
	public void dispose()
	{
		MinecraftForge.EVENT_BUS.unregister(this);
	}
}
