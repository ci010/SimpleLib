package api.simplelib.gui;

import api.simplelib.gui.animation.Controller;
import api.simplelib.gui.components.GuiComponent;
import com.google.common.collect.Lists;
import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.util.Timer;

import java.io.IOException;
import java.util.List;

/**
 * @author ci010
 */
public class GuiScreenCommon extends GuiScreen
{
	protected List<GuiComponent> back;
	protected GuiComponent current;
	protected boolean adjusted;
	protected Timer timer = new Timer();

	public GuiScreenCommon(ComponentProvider provider)
	{
		List<GuiComponent> components = Lists.newArrayList();
		provider.provideComponents(components);
		List<GuiComponent> front = Lists.newArrayList();
		for (GuiComponent component : components)
			if (component.getProperties().property(ComponentAPI.PROP_ON_FRONT).get())
			{
				if (this.back == null)
					this.back = Lists.newArrayList();
				this.back.add(component);
			}
			else
				front.add(component);
		back.addAll(front);
	}

	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException
	{
		super.mouseClicked(mouseX, mouseY, mouseButton);
		if (current != null)
			if (current.hasMouseListener())
				current.getMouseListener().onClick(mouseX, mouseY, mouseButton, this.include(current, mouseX, mouseY));
	}

	@Override
	protected void mouseReleased(int mouseX, int mouseY, int state)
	{
		super.mouseReleased(mouseX, mouseY, state);
		if (current != null)
			if (current.hasMouseListener())
				current.getMouseListener().onRelease(mouseX, mouseY, state);
	}

	@Override
	public void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick)
	{
		super.mouseClickMove(mouseX, mouseY, clickedMouseButton, timeSinceLastClick);
		if (current != null)
			if (current.hasMouseListener())
				current.getMouseListener().onDrag(mouseX, mouseY, clickedMouseButton, timeSinceLastClick);
	}

	@Override
	public void initGui()
	{
		super.initGui();
		if (!adjusted)
		{//// FIXME: 2016/5/11 Fix this new init method
//			for (GuiComponent comp : back)
//				comp.setPos(comp.getX(), comp.getY()).initGui();
			adjusted = true;
		}
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks)
	{
		super.drawScreen(mouseX, mouseY, partialTicks);
		boolean checked = false;
		if (current != null)
			if (this.include(current, mouseX, mouseY))
			{
				checked = true;
				if (current.hasMouseListener())
					current.getMouseListener().onHovered(mouseX, mouseY, timer.getTime());
			}
			else
				timer.reset();
		for (GuiComponent component : back)
		{
			Controller controller = component.getController();
			if (controller != null)
				controller.draw(component);
//			else
//				component.draw();
			if (component.hasMouseListener())
				component.getMouseListener().onMove(mouseX, mouseY);
			if (!checked)
				if (this.include(component, mouseX, mouseY))
				{
					checked = true;
					this.current = component;
				}
		}
	}

	protected boolean include(GuiComponent gui, int x, int y)
	{
		return gui.getX() + gui.getWidth() > x
				&& gui.getX() < x
				&& gui.getY() < y
				&& gui.getY() + gui.getHeight() > y;
	}
}
