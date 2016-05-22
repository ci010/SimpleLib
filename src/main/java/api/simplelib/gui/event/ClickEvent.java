package api.simplelib.gui.event;

import api.simplelib.gui.components.GuiComponent;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.client.event.MouseEvent;

/**
 * @author ci010
 * @see net.minecraftforge.client.event.GuiScreenEvent
 * @see net.minecraftforge.client.event.MouseEvent
 */
public class ClickEvent extends MouseEvt
{
	public ClickEvent(MouseEvent event, GuiComponent component, GuiScreen screen)
	{
		super(event, component, screen);
	}

	public static class Release extends MouseEvt
	{
		public Release(MouseEvent event, GuiComponent component, GuiScreen screen)
		{
			super(event, component, screen);
		}
	}
}
