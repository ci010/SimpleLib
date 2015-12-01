package net.ci010.minecrafthelper.gui;

import net.minecraft.client.Minecraft;

/**
 * @author ci010
 */
public class GuiString extends GuiComponent
{
	private String string;
	private int x, y, color;

	public GuiString(String string, int x, int y)
	{
		this.string = string;
		this.x = x;
		this.y = y;
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
	public void draw()
	{
		this.drawString(Minecraft.getMinecraft().fontRendererObj, string, x, y, color);
	}
}
