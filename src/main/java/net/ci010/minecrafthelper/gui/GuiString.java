package net.ci010.minecrafthelper.gui;

import net.ci010.minecrafthelper.util.GuiUtil;
import org.lwjgl.Sys;

/**
 * @author ci010
 */
public class GuiString extends GuiComponent
{
	private String string;
	private int x, y, width = 0, height, color;

	public GuiString(String string, int x, int y)
	{
		this.string = string;
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
			if (this.string == null)
			{
				System.out.println("holy shit");
				return;

			}
		}
		this.drawString(GuiUtil.font(), string, x, y, color);
	}
}
