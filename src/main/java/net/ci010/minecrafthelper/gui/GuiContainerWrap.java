package net.ci010.minecrafthelper.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.inventory.Container;

import java.io.IOException;
import java.util.List;

/**
 * @author ci010
 */
public abstract class GuiContainerWrap extends GuiContainer
{
	protected GuiComponent current;

	public GuiContainerWrap(Container inventorySlotsIn)
	{
		super(inventorySlotsIn);
	}

	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException
	{
		super.mouseClicked(mouseX, mouseY, mouseButton);
		if (current != null)
			if (current.hasMouseListener())
				current.getMouseListener().onClick(mouseButton);
	}

	@Override
	protected void mouseReleased(int mouseX, int mouseY, int state)
	{
		super.mouseReleased(mouseX, mouseY, state);
		if (current != null)
			if (current.hasMouseListener())
				current.getMouseListener().onHovered();
	}

	protected boolean include(GuiComponent gui, int x, int y)
	{
		return this.isPointInRegion(gui.getX(), gui.getY(), gui.getWidth(), gui.getHeight(), x, y);
	}

	protected void drawHoveringText(List<String> textLines, int x, int y, int headColor)
	{
		if (textLines.isEmpty())
			return;

		GlStateManager.disableRescaleNormal();
		RenderHelper.disableStandardItemLighting();
		GlStateManager.disableLighting();
		GlStateManager.disableDepth();
		int frameLength = 0;
		FontRenderer font = Minecraft.getMinecraft().fontRendererObj;

		for (String s : textLines)
		{
			int sLength = font.getStringWidth(s);

			if (sLength > frameLength)
				frameLength = sLength;
		}

		int xLeft = x + 12;
		int yLeft = y - 12;
		int frameHeight = 8;

		if (textLines.size() > 1)
			frameHeight += 2 + (textLines.size() - 1) * 10;

		if (xLeft + frameLength > this.width)
			xLeft -= 28 + frameLength;

		if (yLeft + frameHeight + 6 > this.height)
			yLeft = this.height - frameHeight - 6;

		this.zLevel = 300.0F;
		this.itemRender.zLevel = 300.0F;
		int borderColor = -267386864;
		//top border
		this.drawGradientRect(xLeft - 3,
				yLeft - 4,
				xLeft + frameLength + 3,
				yLeft - 3,
				borderColor,
				borderColor);
		this.drawGradientRect(xLeft - 3,
				yLeft + frameHeight + 3,
				xLeft + frameLength + 3,
				yLeft + frameHeight + 4,
				borderColor,
				borderColor);
		this.drawGradientRect(xLeft - 3,
				yLeft - 3,
				xLeft + frameLength + 3,
				yLeft + frameHeight + 3,
				borderColor,
				borderColor);
		this.drawGradientRect(xLeft - 4,
				yLeft - 3,
				xLeft - 3,
				yLeft + frameHeight + 3,
				borderColor,
				borderColor);
		this.drawGradientRect(xLeft + frameLength + 3,
				yLeft - 3,
				xLeft + frameLength + 4,
				yLeft + frameHeight + 3,
				borderColor,
				borderColor);
		int startColor = 1347420415;
		int endColor = (startColor & 16711422) >> 1 | startColor & -16777216;
		this.drawGradientRect(xLeft - 3,
				yLeft - 3 + 1,
				xLeft - 3 + 1,
				yLeft + frameHeight + 3 - 1,
				startColor,
				endColor);
		this.drawGradientRect(xLeft + frameLength + 2,
				yLeft - 3 + 1,
				xLeft + frameLength + 3,
				yLeft + frameHeight + 3 - 1,
				startColor,
				endColor);
		this.drawGradientRect(xLeft - 3,
				yLeft - 3,
				xLeft + frameLength + 3,
				yLeft - 3 + 1,
				startColor,
				startColor);
		this.drawGradientRect(xLeft - 3,
				yLeft + frameHeight + 2,
				xLeft + frameLength + 3,
				yLeft + frameHeight + 3,
				endColor,
				endColor);

		for (int num = 0; num < textLines.size(); ++num)
		{
			String text = textLines.get(num);
			if (num == 0)
				font.drawString(text, xLeft, yLeft, headColor);
			else
				font.drawStringWithShadow(text, xLeft, yLeft, -1);

			if (num == 0)
				yLeft += 2;

			yLeft += 10;
		}

		this.zLevel = 0.0F;
		this.itemRender.zLevel = 0.0F;
		GlStateManager.enableLighting();
		GlStateManager.enableDepth();
		RenderHelper.enableStandardItemLighting();
		GlStateManager.enableRescaleNormal();
	}

}
