package net.ci010.minecrafthelper.gui;

import net.ci010.minecrafthelper.util.DrawUtil;

/**
 * @author ci010
 */
public class GuiBar extends GuiComponent
{
	private TileTexture background;
	private TextureInfo texture;
	private int x, y;
	private Catcher target;
	private Direction dir;

	public GuiBar(Catcher target, TextureInfo bar, int x, int y)
	{
		this.target = target;
		this.texture = bar;
		this.x = x;
		this.y = y;
		this.dir = Direction.toRight;
	}

	public GuiBar setDirection(Direction direction)
	{
		this.dir = direction;
		return this;
	}

	public GuiBar setBackground(TextureInfo background, int xOffset, int yOffset)
	{
		this.background = new TileTexture(background, x + xOffset, y + yOffset);
		return this;
	}


	public void draw()
	{
		//TODO handle the other direction
		if (this.background != null)
			background.draw();
		DrawUtil.bindToTexture(texture);
		switch (this.dir)
		{
			case toRight:
				this.drawTexturedModalRect(x, y, texture.getU(), texture.getV(), (int) (texture.getWidth() * target.getPercentage()),
						texture.getHeight());
				break;
			case toLeft:
				int width = (int) (texture.getWidth() * target.getPercentage());
				this.drawTexturedModalRect(x + x - width, y, texture.getU() + texture.getU() - width, texture.getV(),
						width, texture.getHeight());
				break;
			case toTop:
				int height = (int) (texture.getHeight() * target.getPercentage());
				this.drawTexturedModalRect(x, y + y - height, texture.getU(), texture.getV() + texture.getV() - height,
						texture.getWidth(), height);
				break;
			case toBottom:
				this.drawTexturedModalRect(x, y, texture.getU(), texture.getV(), texture.getWidth(),
						(int) (texture.getHeight() * target.getPercentage()));
				break;
		}
	}

	public enum Direction
	{
		toRight, toLeft, toTop, toBottom;
	}

	interface Catcher
	{
		float getPercentage();
	}
}
