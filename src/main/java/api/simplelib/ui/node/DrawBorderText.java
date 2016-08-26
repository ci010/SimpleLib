package api.simplelib.ui.node;

import api.simplelib.ui.Properties;
import api.simplelib.ui.elements.Element;
import com.google.common.collect.Lists;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.RenderItem;
import net.minecraftforge.fml.client.config.GuiUtils;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * @author ci010
 */
public class DrawBorderText extends Gui implements DrawNode
{
	public static final DrawBorderText INSTANCE = new DrawBorderText();

	private DrawBorderText() {}

//	private int calculateHeight(List<CharSequence> contents)
//	{
//		int height = 8;
//		if (contents.size() > 1)
//			height += 2 + (contents.size() - 1) * 10;
//		return height;
//	}

//	private int calculateWidth(List<CharSequence> contents)
//	{
//		int width = 0;
//		for (CharSequence s : contents)
//		{
//			int sLength = Minecraft.getMinecraft().fontRendererObj.getStringWidth(s.toString());
//			if (sLength > width)
//				width = sLength;
//			//find the max length of string which will getResource to frame'target length.
//		}
//		return width;
//	}

//	private void legacy()
//	{
//		int height = component.transform().height, width = component.transform().width;
//
//		int screenWidth = Minecraft.getMinecraft().currentScreen.width;
//		int screenHeight = Minecraft.getMinecraft().currentScreen.height;
//		int xLeft = component.transform().x + 12;
//		int yLeft = component.transform().y - 12;
//
//		if (xLeft + width > screenWidth)
//			xLeft -= 28 + width;
//
//		if (yLeft + height + 6 > screenHeight)
//			yLeft = screenHeight - height - 6;
//		GlStateManager.disableRescaleNormal();
//		RenderHelper.disableStandardItemLighting();
//		GlStateManager.disableLighting();
//		GlStateManager.disableDepth();
//
//		zLevel = 300.0F;
//		RenderItem itemRender = Minecraft.getMinecraft().getRenderItem();
//		itemRender.zLevel = 300.0F;
//		int borderColor = -267386864;
//		//top border
//		drawGradientRect(xLeft - 3,
//				yLeft - 4,
//				xLeft + width + 3,
//				yLeft - 3,
//				borderColor,
//				borderColor);
//		drawGradientRect(xLeft - 3,
//				yLeft + height + 3,
//				xLeft + width + 3,
//				yLeft + height + 4,
//				borderColor,
//				borderColor);
//		drawGradientRect(xLeft - 3,
//				yLeft - 3,
//				xLeft + width + 3,
//				yLeft + height + 3,
//				borderColor,
//				borderColor);
//		drawGradientRect(xLeft - 4,
//				yLeft - 3,
//				xLeft - 3,
//				yLeft + height + 3,
//				borderColor,
//				borderColor);
//		drawGradientRect(xLeft + width + 3,
//				yLeft - 3,
//				xLeft + width + 4,
//				yLeft + height + 3,
//				borderColor,
//				borderColor);
//		int startColor = 1347420415;
//		int endColor = (startColor & 16711422) >> 1 | startColor & -16777216;
//		drawGradientRect(xLeft - 3,
//				yLeft - 3 + 1,
//				xLeft - 3 + 1,
//				yLeft + height + 3 - 1,
//				startColor,
//				endColor);
//		drawGradientRect(xLeft + width + 2,
//				yLeft - 3 + 1,
//				xLeft + width + 3,
//				yLeft + height + 3 - 1,
//				startColor,
//				endColor);
//		drawGradientRect(xLeft - 3,
//				yLeft - 3,
//				xLeft + width + 3,
//				yLeft - 3 + 1,
//				startColor,
//				startColor);
//		drawGradientRect(xLeft - 3,
//				yLeft + height + 2,
//				xLeft + width + 3,
//				yLeft + height + 3,
//				endColor,
//				endColor);
//
////		for (int num = 0; num < contents.size(); ++num)
////		{
////			String text = contents.var(num).toString();
////			Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(text, xLeft, yLeft, 0);
////			if (num == 0)
////				yLeft += 2;
////			yLeft += 10;
////		}
//
//		zLevel = 0.0F;
//		itemRender.zLevel = 0.0F;
//		GlStateManager.enableLighting();
//		GlStateManager.enableDepth();
//		RenderHelper.enableStandardItemLighting();
//		GlStateManager.enableRescaleNormal();
//	}

	@Override
	public boolean apply(@Nonnull Element component)
	{
		Properties properties = component.getProperties();
		for(Element element : component.getChildren())
		{

		}
		List<String> content = Lists.newArrayList();
		Minecraft mc = Minecraft.getMinecraft();
		GuiUtils.drawHoveringText(content, component.transform().x, component.transform().y, mc.displayWidth, mc
				.displayHeight, properties.num("border_text:max_width").getIfPresent(0).intValue(), mc.fontRendererObj);


		return false;
	}
}
