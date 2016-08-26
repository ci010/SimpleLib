package api.simplelib.ui;

import api.simplelib.client.ClientHelper;
import api.simplelib.ui.elements.Element;
import api.simplelib.utils.EventState;
import com.google.common.collect.Lists;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.common.MinecraftForge;
import org.lwjgl.util.Timer;
import org.lwjgl.util.vector.Vector2f;

import java.util.Collection;
import java.util.List;

/**
 * @author ci010
 */
public class ElementWidget
{
	protected Element current;
	protected MouseEvent currentState;
	protected List<Element> root;
	protected Timer hoverTime = new Timer();
	public boolean hidden = false;

	public ElementWidget(Collection<Element> root)
	{
		this.root = Lists.newArrayList(root);
	}

	public void updateHover()
	{
		currentState = ClientHelper.INSTANCE.getMouseState();
		int x, y;
		Vector2f vec = ClientHelper.INSTANCE.globalToScreenCoord(currentState.getX(), currentState.getY());
		x = (int) vec.getX();
		y = (int) vec.getY();
		if (current != null)
			if (current.transform().container(x, y))
				MinecraftForge.EVENT_BUS.post(
						new ElementEvent.Hover(currentState, current, this, EventState.Ongoing, x, y, hoverTime
								.getTime()));
			else
			{
				MinecraftForge.EVENT_BUS.post(
						new ElementEvent.Hover(currentState, current, this, EventState.End, x, y, hoverTime.getTime()));
				hoverTime.pause();
				hoverTime.reset();
				current = null;
			}
		else if ((current = revolveCurrent(root, x, y)) != null)
		{
			hoverTime.resume();
			MinecraftForge.EVENT_BUS.post(
					new ElementEvent.Hover(currentState, current, this, EventState.Start, x, y, 0));
		}


	}

	public void mouseClick(int mouseX, int mouseY, int mouseButton)
	{
		if (current != null)
		{
			int button = currentState.getButton();
			if (button != -1)
				MinecraftForge.EVENT_BUS.post(new ElementEvent.ClickEvent(this.currentState, this.current, this,
						EventState.Start, mouseX, mouseY, mouseButton));
		}
	}

	public void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick)
	{
		if (current != null)
		{
			int button = currentState.getButton();
			if (button != -1)
				MinecraftForge.EVENT_BUS.post(new ElementEvent.ClickEvent(this.currentState, this.current, this,
						EventState.Ongoing, mouseX, mouseY, timeSinceLastClick));
		}
	}

	public void mouseReleased(int mouseX, int mouseY, int state)
	{
		if (current != null)
		{
			int button = currentState.getButton();
			if (button != -1)
				MinecraftForge.EVENT_BUS.post(new ElementEvent.ClickEvent(this.currentState, this.current, this,
						EventState.End, mouseX, mouseY, currentState.getNanoseconds()));
		}
	}

	private Element revolveCurrent(List<Element> lst, int x, int y)
	{
		for(Element element : lst)
			if (element.transform().container(x, y))
			{
				List<Element> children = element.getChildren();
				if (children.isEmpty())
					return element;
				else
					revolveCurrent(children, x, y);
			}
		return null;
	}

	public void draw()
	{
		if (!hidden)
			for(Element element : root)
				element.draw();
	}

}
