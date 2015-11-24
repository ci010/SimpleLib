package net.ci010.minecrafthelper.test;

import net.minecraft.client.gui.Gui;

/**
 * @author ci010
 */
public class ModBar extends Gui implements GuiComponent
{
	private TileTexture background;
	private TextureInfo bar;
	private int x, y;
	private T target;

	public ModBar(T target, TextureInfo bar, int x, int y)
	{
		this.target = target;
		this.bar = bar;
		this.x = x;
		this.y = y;
	}

	public ModBar setBackground(TextureInfo background, int xOffset, int yOffset)
	{
		this.background = new TileTexture(background, x + xOffset, y + yOffset);
		return this;
	}

	public void draw()
	{
		//TODO handle the other direction
		if (this.background != null)
			background.draw();
		DrawUtil.bindToTexture(bar);
		this.drawTexturedModalRect(x, y, bar.getU(), bar.getV(), (int) (bar.getWidth() * target.getPercentage()), bar.getHeight());
	}

	interface T
	{
		float getPercentage();
	}
}
