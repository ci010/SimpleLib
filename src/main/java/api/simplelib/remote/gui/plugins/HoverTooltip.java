package api.simplelib.remote.gui.plugins;

import api.simplelib.remote.gui.components.GuiComponent;
import api.simplelib.remote.gui.event.HoverEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * @author ci010
 */
public class HoverTooltip implements Plugin
{
	private GuiComponent component;
	private long delay;

	@SubscribeEvent
	public void onHover(HoverEvent event)
	{
		if (event.getComponent() != component)
			return;
		if (event.hoverTime() < delay)
			return;
//		event.getComponent().getProperties().property();
//		texts.setPos(event.getMouseX(), event.getMouseY());
	}

	@Override
	public void plugin(GuiComponent component)
	{
		this.component = component;
		MinecraftForge.EVENT_BUS.register(this);
	}

	@Override
	public void dispose()
	{
		MinecraftForge.EVENT_BUS.unregister(this);
	}
}
