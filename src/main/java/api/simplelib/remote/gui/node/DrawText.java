package api.simplelib.remote.gui.node;

import api.simplelib.Pipeline;
import api.simplelib.lang.Local;
import api.simplelib.remote.gui.Properties;
import api.simplelib.remote.gui.components.GuiComponent;
import api.simplelib.remote.gui.components.GuiText;
import api.simplelib.utils.ArrayUtils;
import com.google.common.collect.Lists;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

/**
 * @author ci010
 */
public class DrawText extends Gui implements DrawNode
{
	public static final DrawText INSTANCE = new DrawText();

	private String[] stringBuffer = ArrayUtils.emptyArray();

	private DrawText() {}

	@Override
	public void draw(GuiComponent.Transform transform, Pipeline<DrawNode> pipeline, Properties properties)
	{
		if (properties instanceof GuiText.Prop)
		{
			GuiText.Prop prop = (GuiText.Prop) properties;
			if (prop.getCacheString() == null)
				prop.setCacheString(buildCacheTexts(prop.getRawString()));
			FontRenderer renderer = prop.getFontRender();
			if (renderer == null)
				renderer = Minecraft.getMinecraft().fontRendererObj;
			this.renderTextBlock(transform.x, transform.y, prop, renderer);
		}
		else
		{
			//adapter
		}
	}

	private CharSequence buildCacheTexts(String[] strings)
	{
		StringBuilder builder = new StringBuilder();
		List<String> list = Lists.newArrayList();
		for (String string : strings)
		{
			list.addAll(Arrays.asList(string.split("\n")));
			for (int i = 0; i < list.size(); i++)
			{
				String s = list.get(i);
				if (s.startsWith("$"))
					list.set(i, Local.trans(s.substring(1)));
			}
		}
		for (String s : list)
			builder.append(s).append("\n");
		return builder;
	}

	private void fitIntoArgs(CharSequence cache, Object[] args)
	{
		stringBuffer = String.format(cache.toString(), args).split("\n");
	}

	private void renderTextBlock(int x, int y, GuiText.Prop properties, FontRenderer renderer)
	{
		int currentY = y;
		Integer height_offset = properties.getCache("height_offset");
		if (height_offset == null) height_offset = 2;
		int realHeight = renderer.FONT_HEIGHT + height_offset;
		if (properties.isDirty())
		{
			this.fitIntoArgs(properties.getCacheString(), properties.getArgs());
			List<String> list = Lists.newArrayList();
			for (int i = 0; i < stringBuffer.length; i++)
			{
				String content = stringBuffer[i];
				Integer width = properties.getCache("text_width");
				if (width != null)
					list.addAll(renderer.listFormattedStringToWidth(content, width));
				else
					list.add(content);
			}
			properties.setRenderString(list.toArray(new String[list.size()]));
			properties.markDirty(false);
		}

		String[] renderString = properties.getRenderString();
		int cursIdx = properties.cursor(), startIdx = properties.startCursor();
		boolean selecting = startIdx != -1;
		for (String s : renderString)
		{
			int length = s.length();
			boolean cursorInLine = cursIdx < length + 1;
			if (!cursorInLine)
				cursIdx -= (length + 1);
			if (selecting)
				if (startIdx > length + 1)
					startIdx -= length + 1;
				else
				{
					int startX = x;
					if (startIdx > 0)
					{
						startX += renderer.getStringWidth(s.substring(0, startIdx));
						startIdx = 0;
					}
					if (!cursorInLine)
						Gui.drawRect(startX, currentY, startX + renderer.getStringWidth(s), renderer.FONT_HEIGHT, Color.BLUE.getRGB());
					else
						Gui.drawRect(startX, currentY, startX + renderer.getStringWidth(s.substring(0, cursIdx)), renderer.FONT_HEIGHT, Color.BLUE.getRGB());
				}
			renderer.drawStringWithShadow(s, x, currentY, 0);
			currentY += realHeight;
			if (cursorInLine)
				renderer.drawString("|", renderer.getStringWidth(s.substring(0, cursIdx)), currentY, 0);
		}
	}
}
