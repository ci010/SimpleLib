package net.simplelib.gui;

import net.simplelib.util.GuiUtil;

/**
 * The bar-like Component.
 * The effect of this is like the progress bar in furnace.
 *
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

	/**
	 * @param direction The direction of the bar will move.
	 * @return this
	 */
	public GuiBar setDirection(Direction direction)
	{
		this.dir = direction;
		return this;
	}

	/**
	 * @param background The texture of the background of the bar.
	 * @param xOffset The offset related to the x position of this bar.
	 * @param yOffset The offset related to the y position of this bar.
	 * @return this
	 */
	public GuiBar setBackground(TextureInfo background, int xOffset, int yOffset)
	{
		this.background = new TileTexture(background, x + xOffset, y + yOffset);
		return this;
	}

	public void draw()
	{
		if (this.background != null)
			background.draw();
		GuiUtil.bindToTexture(texture);
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

	@Override
	public int getWidth()
	{
		return background.getWidth();
	}

	@Override
	public int getHeight()
	{
		return background.getHeight();
	}

	public enum Direction
	{
		toRight, toLeft, toTop, toBottom;
	}

	/**
	 * This interface provides the percentage of the progress.
	 * For instance, if the it return 0.5, the bar will be drawn in half.
	 */
	interface Catcher
	{
		float getPercentage();
	}
}
