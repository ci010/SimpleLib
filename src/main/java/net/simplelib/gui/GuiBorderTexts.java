package net.simplelib.gui;

import com.google.common.collect.Lists;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.StatCollector;
import net.simplelib.HelperMod;

import java.util.IllegalFormatException;
import java.util.List;

/**
 * @author ci010
 */
public class GuiBorderTexts extends GuiComponent
{
	private FontRenderer font;
	private RenderItem itemRender;
	protected int height, width, xLeft, yLeft;
	protected List<String> contents, keyLines = Lists.newArrayList();
	protected List<GuiString.TextSource> sources = Lists.newArrayList();

	public GuiBorderTexts addTextLine(String contentKey)
	{
		keyLines.add(contentKey);
		sources.add(null);
		return this;
	}

	public GuiBorderTexts addTextLine(String contentKey, GuiString.TextSource source)
	{
		keyLines.add(contentKey);
		sources.add(source);
		return this;
	}

	@Override
	public int getWidth()
	{
		return width;
	}

	@Override
	public int getHeight()
	{
		return height;
	}

	@Override
	public void initGui()
	{
		font = Minecraft.getMinecraft().fontRendererObj;
		this.itemRender = Minecraft.getMinecraft().getRenderItem();
		int screenWidth = Minecraft.getMinecraft().currentScreen.width;
		int screenHeight = Minecraft.getMinecraft().currentScreen.height;
		contents = Lists.newArrayList();
		int size = this.keyLines.size();
		for (int i = 0; i < size; ++i)
		{
			String localized = StatCollector.translateToLocal(keyLines.get(i));
			GuiString.TextSource source = this.sources.get(i);
			if (source != null)
				try
				{
					localized = String.format(localized, source.getArguments());
				}
				catch (IllegalFormatException illegalformatexception)
				{
					HelperMod.LOG.fatal("Format error: " + localized);
				}
			localized = new ChatComponentText(localized).getFormattedText();
			contents.add(localized);
		}

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

		xLeft = x + 12;
		yLeft = y - 12;

		if (xLeft + this.width > screenWidth)
			xLeft -= 28 + this.width;

		if (yLeft + this.height + 6 > screenHeight)
			yLeft = screenHeight - this.height - 6;
	}

	@Override
	public void draw()
	{
		if (contents.isEmpty())
			return;

		GlStateManager.disableRescaleNormal();
		RenderHelper.disableStandardItemLighting();
		GlStateManager.disableLighting();
		GlStateManager.disableDepth();

		this.zLevel = 300.0F;
		this.itemRender.zLevel = 300.0F;
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
		this.itemRender.zLevel = 0.0F;
		GlStateManager.enableLighting();
		GlStateManager.enableDepth();
		RenderHelper.enableStandardItemLighting();
		GlStateManager.enableRescaleNormal();
	}
}
