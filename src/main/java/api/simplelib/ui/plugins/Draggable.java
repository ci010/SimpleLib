package api.simplelib.ui.plugins;

import api.simplelib.ui.elements.Element;
import api.simplelib.ui.ElementEvent;
import api.simplelib.utils.EventState;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * @author ci010
 */
public class Draggable implements Plugin
{
	private Element element;

	public Draggable(Element element)
	{
		this.element = element;
	}

	@SubscribeEvent
	public void onDrag(ElementEvent.DragEvent event)
	{
		if (event.getElement() != element)
			return;
		if (event.getEventState() == EventState.Ongoing)
		{
			int adjustX = event.getMouseX() - element.transform().x;
			int adjustY = event.getMouseY() - element.transform().y;
			element.transform().translate(-adjustX, -adjustY);
		}
	}

	@Override
	public void plugin(final Element component)
	{
		MinecraftForge.EVENT_BUS.register(this);
	}

	@Override
	public void dispose(Element element)
	{
		MinecraftForge.EVENT_BUS.unregister(this);
	}
}
