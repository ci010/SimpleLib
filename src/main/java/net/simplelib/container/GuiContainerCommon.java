package net.simplelib.container;


import api.simplelib.ui.ElementWidget;
import api.simplelib.ui.GuiDocument;
import api.simplelib.ui.elements.Element;
import api.simplelib.ui.elements.TextureBlock;
import api.simplelib.utils.GuiUtil;
import api.simplelib.vars.VarRef;
import com.google.common.collect.Lists;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

/**
 * @author ci010
 */
public class GuiContainerCommon extends GuiContainer
{
	protected List<Element> front, back;
//	protected boolean adjusted;
	protected ElementWidget widget;
	protected GuiDocument document;

	public GuiContainerCommon(Container inventorySlotsIn, ICapabilityProvider provider)
	{
		super(inventorySlotsIn);
		GuiDocument capability = provider.getCapability(GuiDocument.CAPABILITY, null);

		if (capability != null)
		{
			Collection<Element> roots = capability.getRoots();
			this.widget = new ElementWidget(roots);
			this.document = capability;
		}
		front = Lists.newArrayList();
		back = Lists.newArrayList();
	}

	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException
	{
		super.mouseClicked(mouseX, mouseY, mouseButton);
		widget.mouseClick(mouseX, mouseY, mouseButton);
	}

	@Override
	protected void mouseReleased(int mouseX, int mouseY, int state)
	{
		super.mouseReleased(mouseX, mouseY, state);
		widget.mouseReleased(mouseX, mouseY, state);
	}

	@Override
	public void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick)
	{
		super.mouseClickMove(mouseX, mouseY, clickedMouseButton, timeSinceLastClick);
		widget.mouseClickMove(mouseX, mouseY, clickedMouseButton, timeSinceLastClick);
	}

	@Override
	public void initGui()
	{
		super.initGui();
		if (front != null)
			front.clear();
		if (back != null)
			back.clear();

		for(Slot slot : inventorySlots.inventorySlots)
			back.add(new TextureBlock(GuiUtil.slot, slot.xDisplayPosition, slot.yDisplayPosition));

		for(Element element : this.document.getAll())
		{
			VarRef<Boolean> onFront = element.getProperties().bool("onFront");
			if (onFront.isPresent() && onFront.get())
				front.add(element);
			else back.add(element);
		}
		for(Slot slot : inventorySlots.inventorySlots)
			back.add(new TextureBlock(GuiUtil.slot, slot.xDisplayPosition, slot.yDisplayPosition));
//		if (!adjusted)
//		{
//			for(Element comp : back)
//				comp.transform().setPos(this.guiLeft + comp.getX() - 1, this.guiTop + comp.getY() - 1).initGui();
//			for(Element comp : front)
//				comp.transform().setPos(this.guiLeft + comp.getX() - 1, this.guiTop + comp.getY() - 1).initGui();
//			adjusted = true;
//		}
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{
		for(Element Element : front)
			Element.draw();
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
		for(Element Element : back)
			Element.draw();
	}

	@Override
	public void handleMouseInput() throws IOException
	{
		super.handleMouseInput();
		widget.updateHover();
	}
}
