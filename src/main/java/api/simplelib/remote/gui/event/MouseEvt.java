package api.simplelib.remote.gui.event;

import api.simplelib.remote.gui.components.GuiComponent;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.fml.common.eventhandler.Event;

/**
 * @author ci010
 * @see net.minecraftforge.client.event.GuiScreenEvent
 * @see net.minecraftforge.client.event.MouseEvent
 */
class MouseEvt extends Event
{
	private MouseEvent event;
	private GuiComponent component;
	private GuiScreen screen;

	public MouseEvt(MouseEvent event, GuiComponent component, GuiScreen screen)
	{
		this.event = event;
		this.component = component;
		this.screen = screen;
	}

	public int getMouseX()
	{
		return event.x;
	}

	public int getMouseY()
	{
		return event.y;
	}

	public MouseEvent getFullState()
	{
		return event;
	}

	public GuiComponent getComponent()
	{
		return component;
	}

	public GuiScreen getScreen()
	{
		return screen;
	}
}
