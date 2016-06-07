package api.simplelib.remote.gui.event;

import api.simplelib.remote.gui.components.GuiComponent;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.client.event.MouseEvent;

/**
 * @author ci010
 * @see net.minecraftforge.client.event.GuiScreenEvent
 * @see net.minecraftforge.client.event.MouseEvent
 */
public class HoverEvent extends MouseEvt
{
	protected static long lastTime;

	public HoverEvent(MouseEvent event, GuiComponent component, GuiScreen screen)
	{
		super(event, component, screen);
		lastTime = this.hoverTime();
	}

	public long hoverTime()
	{
		return this.getFullState().nanoseconds;
	}

	public static class End extends MouseEvt
	{
		public End(MouseEvent event, GuiComponent component, GuiScreen screen)
		{
			super(event, component, screen);
		}

		public long getTotalHoverTime()
		{
			return lastTime;
		}
	}
}
