package net.ci010.minecrafthelper.gui;

import net.ci010.minecrafthelper.util.GuiUtil;
import net.minecraft.util.StatCollector;

/**
 * @author ci010
 */
public class GuiString extends GuiComponent
{
	protected String key;
	protected int x, y, width = 0, height, color;

	public GuiString(String key, int x, int y)
	{
		this.key = key;
		this.x = x;
		this.y = y;
		this.height = 8;
		this.color = 10526880;//TODO see what the black is
	}

	public GuiString color(int color)
	{
		this.color = color;
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
	public void draw()
	{
		if (this.width == 0)
		{
			if (GuiUtil.font() == null)
			{
				System.out.println("WTF");
				return;
			}
			if (this.key == null)
			{
				System.out.println("holy shit");
				return;
			}
		}
		this.drawString(GuiUtil.font(), StatCollector.translateToLocal(key), x, y, color);
	}
}
