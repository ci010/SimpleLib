package api.simplelib.remote.gui.plugins;

import api.simplelib.remote.gui.components.GuiComponent;
import api.simplelib.remote.gui.event.DragEvent;
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
			adjustX = event.getMouseX() - gui.transform().x;
			adjustY = event.getMouseY() - gui.transform().y;
		}
		gui.transform().offset(-adjustX, -adjustY);
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
