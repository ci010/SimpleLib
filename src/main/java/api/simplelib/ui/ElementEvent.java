package api.simplelib.ui;

import api.simplelib.ui.elements.Element;
import api.simplelib.utils.EventState;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.fml.common.eventhandler.Event;

/**
 * @author ci010
 * @see net.minecraftforge.client.event.GuiScreenEvent
 * @see net.minecraftforge.client.event.MouseEvent
 */
public class ElementEvent extends Event
{
	private MouseEvent event;
	private Element component;
	private ElementWidget widget;
	private EventState eventState;
	private int x, y;

	private ElementEvent(MouseEvent event, Element component, ElementWidget widget, EventState eventState, int x, int y)
	{
		this.event = event;
		this.component = component;
		this.widget = widget;
		this.eventState = eventState;
		this.x = x;
		this.y = y;
	}

	public EventState getEventState()
	{
		return eventState;
	}

	public int getMouseX()
	{
		return y;
	}

	public int getMouseY()
	{
		return x;
	}

	public MouseEvent getFullState()
	{
		return event;
	}

	public Element getElement()
	{
		return component;
	}

	public ElementWidget getWidget()
	{
		return widget;
	}

	/**
	 * @author ci010
	 * @see net.minecraftforge.client.event.GuiScreenEvent
	 * @see MouseEvent
	 */
	public static class ClickEvent extends ElementEvent
	{
		private long clickTime;

		public ClickEvent(MouseEvent event, Element component, ElementWidget widget, EventState eventState, int x,
						  int y, long time)
		{
			super(event, component, widget, eventState, x, y);
			this.clickTime = time;
		}

		public long getClickedTime()
		{
			return clickTime;
		}
	}

	/**
	 * @author ci010
	 * @see net.minecraftforge.client.event.GuiScreenEvent
	 * @see MouseEvent
	 */
	public static class DragEvent extends ElementEvent
	{
		public DragEvent(MouseEvent event, Element component, ElementWidget widget, EventState eventState, int x, int y)
		{
			super(event, component, widget, eventState, x, y);
		}

		public long getDraggingTime()
		{
			return this.getFullState().getNanoseconds();
		}
	}

	/**
	 * @author ci010
	 * @see net.minecraftforge.client.event.GuiScreenEvent
	 * @see MouseEvent
	 */
	public static class Hover extends ElementEvent
	{
		private float time;

		public Hover(MouseEvent event, Element component, ElementWidget widget, EventState eventState, int x, int y,
					 float time)
		{
			super(event, component, widget, eventState, x, y);
			this.time = time;
		}

		public float getHoverTime()
		{
			return time;
		}
	}
}
