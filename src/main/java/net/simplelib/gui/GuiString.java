package net.simplelib.gui;

import net.minecraft.util.ChatComponentText;
import net.minecraft.util.StatCollector;
import net.simplelib.HelperMod;
import net.simplelib.util.GuiUtil;

import java.util.IllegalFormatException;

/**
 * One of the most basic components in gui.
 * This just simply draw a string.
 *
 * @author ci010
 */
public class GuiString extends GuiComponent
{
	protected String key;
	protected String content;
	protected int x, y, width = 0, height;
	protected TextSource source;

	public GuiString(String key, int x, int y)
	{
		this.key = key;
		this.x = x;
		this.y = y;
		this.height = 8;
		if (!StatCollector.canTranslate(this.key))
		{

		}
	}

	public GuiString setSource(TextSource source)
	{
		this.source = source;
		return this;
	}

	@Override
	public Type type()
	{
		return Type.front;
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
		String localized = StatCollector.translateToLocal(this.key);
		if (this.source != null)
			try
			{
				localized = String.format(localized, this.source.getArguments());
			}
			catch (IllegalFormatException illegalformatexception)
			{
				HelperMod.LOG.fatal("Format error: " + localized);
			}
		localized = new ChatComponentText(localized).getFormattedText();
		int width;
		if (this.width != (width = GuiUtil.font().getStringWidth(localized)))
		{
			this.width = width;
			this.content = localized;
		}
	}

	public interface TextSource
	{
		Object[] getArguments();
	}

	@Override
	public void draw()
	{
		this.drawString(GuiUtil.font(), content, x, y, 0);
	}
}
