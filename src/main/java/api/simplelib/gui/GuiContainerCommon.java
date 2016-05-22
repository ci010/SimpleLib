package api.simplelib.gui;

import api.simplelib.gui.components.GuiTextureBlock;
import api.simplelib.gui.event.ClickEvent;
import api.simplelib.gui.event.DragEvent;
import api.simplelib.gui.event.HoverEvent;
import api.simplelib.utils.GuiUtil;
import api.simplelib.gui.components.GuiComponent;
import com.google.common.collect.Lists;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.common.MinecraftForge;
import org.lwjgl.util.Timer;

import java.io.IOException;
import java.util.List;

/**
 * @author ci010
 */
public class GuiContainerCommon extends GuiContainer
{
	protected List<GuiComponent> front, back;
	protected GuiComponent current;
	protected boolean adjusted;
	protected MouseEvent currentState;

	public GuiContainerCommon(Container container)
	{
		super(container);
	}

	public GuiContainerCommon loadGui(ComponentProvider provider)
	{
		if (front != null)
			front.clear();
		if (back != null)
			back.clear();
		List<GuiComponent> components = Lists.newArrayList();
		provider.provideComponents(components);
		for (GuiComponent component : components)
			if (component.getProperties().property(ComponentAPI.PROP_ON_FRONT).get())
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
		for (Object o : inventorySlots.inventorySlots)
		{
			Slot slot = (Slot) o;
			back.add(new GuiTextureBlock(GuiUtil.slot, slot.xDisplayPosition, slot.yDisplayPosition));
		}
		return this;
	}

	public GuiContainerCommon(Container inventorySlotsIn, ComponentProvider provider)
	{
		super(inventorySlotsIn);
		List<GuiComponent> components = Lists.newArrayList();
		provider.provideComponents(components);
		for (GuiComponent component : components)
			if (component.getProperties().property(ComponentAPI.PROP_ON_FRONT).get())
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
			back.add(new GuiTextureBlock(GuiUtil.slot, slot.xDisplayPosition, slot.yDisplayPosition));
		}
	}

	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException
	{
		super.mouseClicked(mouseX, mouseY, mouseButton);
		if (current != null)
			MinecraftForge.EVENT_BUS.post(new ClickEvent(this.currentState, current, this));
	}

	@Override
	protected void mouseReleased(int mouseX, int mouseY, int state)
	{
		super.mouseReleased(mouseX, mouseY, state);
		if (current != null)
			MinecraftForge.EVENT_BUS.post(new ClickEvent.Release(this.currentState, current, this));
	}

	@Override
	public void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick)
	{
		super.mouseClickMove(mouseX, mouseY, clickedMouseButton, timeSinceLastClick);
		if (current != null)
			MinecraftForge.EVENT_BUS.post(new DragEvent(this.currentState, current, this));
	}

	@Override
	public void initGui()
	{
		super.initGui();
		if (!adjusted)
		{
//			for (GuiComponent comp : back)
//				comp.setPos(this.guiLeft + comp.getX() - 1, this.guiTop + comp.getY() - 1).initGui();
//			for (GuiComponent comp : front)
//				comp.setPos(this.guiLeft + comp.getX() - 1, this.guiTop + comp.getY() - 1).initGui();
			adjusted = true;
		}
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{
		for (GuiComponent guiComponent : front)
			guiComponent.draw();
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
		for (GuiComponent guiComponent : back)
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
				MinecraftForge.EVENT_BUS.post(new HoverEvent(currentState, current, this));
			}
			else
				MinecraftForge.EVENT_BUS.post(new HoverEvent.End(currentState, current, this));
		for (GuiComponent component : front)
		{
			if (!checked)
				if (this.include(component, mouseX, mouseY))
				{
					checked = true;
					this.current = component;
				}
		}
		for (GuiComponent component : back)
		{
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

	public void accept(MouseEvent event)
	{
		this.currentState = event;
	}
}
