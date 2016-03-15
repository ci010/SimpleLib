package api.simplelib.gui;

import com.google.common.collect.Lists;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import api.simplelib.utils.GuiUtil;
import org.lwjgl.util.Timer;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author ci010
 */
public class GuiContainerCommon extends GuiContainer
{
	protected List<GuiComponent> front, back;
	protected GuiComponent current;
	protected boolean adjusted;
	protected Timer timer = new Timer();

	public GuiContainerCommon(Container inventorySlotsIn, GuiProvider provider)
	{
		super(inventorySlotsIn);
		List<GuiComponent> components = Lists.newArrayList();
		provider.provideComponents(components);
		Comparator<GuiComponent> comp = new Comparator<GuiComponent>()
		{
			@Override
			public int compare(GuiComponent o1, GuiComponent o2)
			{
				return o1.priority().getWeight() - o2.priority().getWeight();
			}
		};
		for (GuiComponent component : components)
			if (component.type() == GuiComponent.Type.front)
			{
				if (front == null)
					front = Lists.newArrayList();
				front.add(component);
			}
			else
			{
				if (back == null)
					back = Lists.newArrayList();
				back.add(component);
			}
		for (Object o : inventorySlotsIn.inventorySlots)
		{
			Slot slot = (Slot) o;
			back.add(new TileTexture(GuiUtil.slot, slot.xDisplayPosition, slot.yDisplayPosition));
		}
		Collections.sort(front, comp);
		Collections.sort(back, comp);
	}

	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException
	{
		super.mouseClicked(mouseX, mouseY, mouseButton);
		if (current != null)
			if (current.hasMouseListener())
				current.getMouseListener().onClick(mouseX, mouseY, mouseButton);
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
		{
			for (GuiComponent comp : back)
				comp.setPos(this.guiLeft + comp.getX() - 1, this.guiTop + comp.getY() - 1).initGui();
			for (GuiComponent comp : front)
				comp.setPos(this.guiLeft + comp.getX() - 1, this.guiTop + comp.getY() - 1).initGui();
			adjusted = true;
		}
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{
		for (Drawable guiComponent : front)
			guiComponent.draw();

	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
		for (Drawable guiComponent : back)
			guiComponent.draw();
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
		for (GuiComponent component : front)
		{
			if (component.hasMouseListener())
				component.getMouseListener().onMove(mouseX, mouseY);
			if (!checked)
				if (this.include(component, mouseX, mouseY))
				{
					checked = true;
					this.current = component;
				}
		}
		for (GuiComponent component : back)
		{
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
		return this.isPointInRegion(gui.getX(), gui.getY(), gui.getWidth(), gui.getHeight(), x, y);
	}
}
