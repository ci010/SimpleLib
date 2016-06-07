package api.simplelib.remote.gui.node;

import api.simplelib.Pipeline;
import api.simplelib.remote.gui.Properties;
import api.simplelib.remote.gui.components.GuiComponent;
import api.simplelib.vars.Var;
import api.simplelib.vars.VarForward;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;

import java.util.List;

/**
 * @author ci010
 */
public class DrawBorder extends Gui implements DrawNode
{
	public static final DrawBorder INSTANCE = new DrawBorder();

	private DrawBorder() {}

	private int calculateHeight(List<CharSequence> contents)
	{
		int height = 8;
		if (contents.size() > 1)
			height += 2 + (contents.size() - 1) * 10;
		return height;
	}

	private int calculateWidth(List<CharSequence> contents)
	{
		int width = 0;
		for (CharSequence s : contents)
		{
			int sLength = Minecraft.getMinecraft().fontRendererObj.getStringWidth(s.toString());
			if (sLength > width)
				width = sLength;
			//find the max length of string which will apply to frame'target length.
		}
		return width;
	}

	@Override
	public void draw(GuiComponent.Transform transform, Pipeline<DrawNode> pipeline, Properties properties)
	{
		VarForward<List<CharSequence>> property = null;
		// /properties.property(ComponentAPI.PROP_LIST_STRING);
		if (!property.isPresent())
			return;
		List<CharSequence> contents = property.get();
		if (contents.isEmpty())
			return;

		String HEIGHT = "border_height";
		String WIDTH = "border_width";
		Var<Integer> heightContainer = properties.getCache(HEIGHT),
				widthContainer = properties.getCache(WIDTH);
		int height, width;
		if (heightContainer.get() == null)
			heightContainer.set(height = calculateHeight(contents));
		else
			height = heightContainer.get();
		if (widthContainer.get() == null)
			widthContainer.set(width = calculateWidth(contents));
		else
			width = widthContainer.get();

		int screenWidth = Minecraft.getMinecraft().currentScreen.width;
		int screenHeight = Minecraft.getMinecraft().currentScreen.height;
		int xLeft = transform.x + 12;
		int yLeft = transform.y - 12;

		if (xLeft + width > screenWidth)
			xLeft -= 28 + width;

		if (yLeft + height + 6 > screenHeight)
			yLeft = screenHeight - height - 6;
		GlStateManager.disableRescaleNormal();
		RenderHelper.disableStandardItemLighting();
		GlStateManager.disableLighting();
		GlStateManager.disableDepth();

		zLevel = 300.0F;
		RenderItem itemRender = Minecraft.getMinecraft().getRenderItem();
		itemRender.zLevel = 300.0F;
		int borderColor = -267386864;
		//top border
		drawGradientRect(xLeft - 3,
				yLeft - 4,
				xLeft + width + 3,
				yLeft - 3,
				borderColor,
				borderColor);
		drawGradientRect(xLeft - 3,
				yLeft + height + 3,
				xLeft + width + 3,
				yLeft + height + 4,
				borderColor,
				borderColor);
		drawGradientRect(xLeft - 3,
				yLeft - 3,
				xLeft + width + 3,
				yLeft + height + 3,
				borderColor,
				borderColor);
		drawGradientRect(xLeft - 4,
				yLeft - 3,
				xLeft - 3,
				yLeft + height + 3,
				borderColor,
				borderColor);
		drawGradientRect(xLeft + width + 3,
				yLeft - 3,
				xLeft + width + 4,
				yLeft + height + 3,
				borderColor,
				borderColor);
		int startColor = 1347420415;
		int endColor = (startColor & 16711422) >> 1 | startColor & -16777216;
		drawGradientRect(xLeft - 3,
				yLeft - 3 + 1,
				xLeft - 3 + 1,
				yLeft + height + 3 - 1,
				startColor,
				endColor);
		drawGradientRect(xLeft + width + 2,
				yLeft - 3 + 1,
				xLeft + width + 3,
				yLeft + height + 3 - 1,
				startColor,
				endColor);
		drawGradientRect(xLeft - 3,
				yLeft - 3,
				xLeft + width + 3,
				yLeft - 3 + 1,
				startColor,
				startColor);
		drawGradientRect(xLeft - 3,
				yLeft + height + 2,
				xLeft + width + 3,
				yLeft + height + 3,
				endColor,
				endColor);

//		for (int num = 0; num < contents.size(); ++num)
//		{
//			String text = contents.get(num).toString();
//			Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(text, xLeft, yLeft, 0);
//			if (num == 0)
//				yLeft += 2;
//			yLeft += 10;
//		}

		zLevel = 0.0F;
		itemRender.zLevel = 0.0F;
		GlStateManager.enableLighting();
		GlStateManager.enableDepth();
		RenderHelper.enableStandardItemLighting();
		GlStateManager.enableRescaleNormal();
	}
}
