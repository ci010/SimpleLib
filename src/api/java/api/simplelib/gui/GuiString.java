package api.simplelib.gui;

import net.minecraft.util.ChatComponentText;
import api.simplelib.utils.GuiUtil;

/**
 * One of the most basic components in gui.
 * This just simply draw a string.
 *
 * @author ci010
 */
public class GuiString extends GuiComponent
{
	protected Object key;
	protected String content;
	protected int x, y, width = 0, height;

	public GuiString(Object key, int x, int y)
	{
		this.key = key;
		this.x = x;
		this.y = y;
		this.height = 8;
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
		String localized = new ChatComponentText(key.toString()).getFormattedText();
		int width;
		if (this.width != (width = GuiUtil.font().getStringWidth(localized)))
		{
			this.width = width;
			this.content = localized;
		}
	}

	@Override
	public void draw()
	{
		this.drawString(GuiUtil.font(), content, x, y, 0);
	}
}
