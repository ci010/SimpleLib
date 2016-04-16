package net.simplelib.gui;

import com.google.common.collect.Lists;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.util.ChatComponentText;

import java.util.List;

/**
 * @author ci010
 */
public class DrawBorderText extends Gui implements Drawer
{
	private FontRenderer font;
	protected int height, width, xLeft, yLeft;
	protected List<String> contents;
	protected List<Object> keyLines = Lists.newArrayList();

	public DrawBorderText()
	{
		font = Minecraft.getMinecraft().fontRendererObj;
		contents = Lists.newArrayList();
		for (Object keyLine : this.keyLines)
			contents.add(new ChatComponentText(keyLine.toString()).getUnformattedText());

		this.width = 0;
		for (String s : contents)
		{
			int sLength = font.getStringWidth(s);
			if (sLength > this.width)
				this.width = sLength;
			//find the max length of string which will apply to frame's length.
		}

		this.height = 8;
		if (contents.size() > 1)
			this.height += 2 + (contents.size() - 1) * 10;


	}

	@Override
	public void draw(int x, int y, float scale)
	{
		if (contents.isEmpty())
			return;

		int screenWidth = Minecraft.getMinecraft().currentScreen.width;
		int screenHeight = Minecraft.getMinecraft().currentScreen.height;
		xLeft = x + 12;
		yLeft = y - 12;

		if (xLeft + this.width > screenWidth)
			xLeft -= 28 + this.width;

		if (yLeft + this.height + 6 > screenHeight)
			yLeft = screenHeight - this.height - 6;

		GlStateManager.disableRescaleNormal();
		RenderHelper.disableStandardItemLighting();
		GlStateManager.disableLighting();
		GlStateManager.disableDepth();

		this.zLevel = 300.0F;
		RenderItem itemRender = Minecraft.getMinecraft().getRenderItem();
		itemRender.zLevel = 300.0F;
		int borderColor = -267386864;
		//top border
		this.drawGradientRect(xLeft - 3,
				yLeft - 4,
				xLeft + width + 3,
				yLeft - 3,
				borderColor,
				borderColor);
		this.drawGradientRect(xLeft - 3,
				yLeft + this.height + 3,
				xLeft + width + 3,
				yLeft + this.height + 4,
				borderColor,
				borderColor);
		this.drawGradientRect(xLeft - 3,
				yLeft - 3,
				xLeft + width + 3,
				yLeft + this.height + 3,
				borderColor,
				borderColor);
		this.drawGradientRect(xLeft - 4,
				yLeft - 3,
				xLeft - 3,
				yLeft + this.height + 3,
				borderColor,
				borderColor);
		this.drawGradientRect(xLeft + width + 3,
				yLeft - 3,
				xLeft + width + 4,
				yLeft + this.height + 3,
				borderColor,
				borderColor);
		int startColor = 1347420415;
		int endColor = (startColor & 16711422) >> 1 | startColor & -16777216;
		this.drawGradientRect(xLeft - 3,
				yLeft - 3 + 1,
				xLeft - 3 + 1,
				yLeft + this.height + 3 - 1,
				startColor,
				endColor);
		this.drawGradientRect(xLeft + width + 2,
				yLeft - 3 + 1,
				xLeft + width + 3,
				yLeft + this.height + 3 - 1,
				startColor,
				endColor);
		this.drawGradientRect(xLeft - 3,
				yLeft - 3,
				xLeft + width + 3,
				yLeft - 3 + 1,
				startColor,
				startColor);
		this.drawGradientRect(xLeft - 3,
				yLeft + this.height + 2,
				xLeft + width + 3,
				yLeft + this.height + 3,
				endColor,
				endColor);

		for (int num = 0; num < contents.size(); ++num)
		{
			String text = contents.get(num);
			font.drawStringWithShadow(text, xLeft, yLeft, 0);
			if (num == 0)
				yLeft += 2;
			yLeft += 10;
		}

		this.zLevel = 0.0F;
		itemRender.zLevel = 0.0F;
		GlStateManager.enableLighting();
		GlStateManager.enableDepth();
		RenderHelper.enableStandardItemLighting();
		GlStateManager.enableRescaleNormal();
	}
}
