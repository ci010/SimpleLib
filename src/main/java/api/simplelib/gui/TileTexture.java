package api.simplelib.gui;

import api.simplelib.utils.GuiUtil;

/**
 * The most basic implementation of {@link GuiComponent} using {@link TextureInfo}.
 * This component just draws the texture in a specific position.
 *
 * @author ci010
 */
public class TileTexture extends GuiComponent
{
	private TextureInfo texture;

	/**
	 * @param texture The texture will be drawn.
	 * @param x       The texture x position.
	 * @param y       The texture y position.
	 */
	public TileTexture(TextureInfo texture, int x, int y)
	{
		this.texture = texture;
		this.x = x;
		this.y = y;
	}

	@Override
	public int getWidth()
	{
		return (int) (texture.getWidth());
	}

	@Override
	public int getHeight()
	{
		return (int) (texture.getHeight());
	}

	@Override
	public void draw()
	{
		GuiUtil.bindToTexture(texture);
		this.drawTexturedModalRect(x, y,
				texture.getU(), texture.getV(), this.getHeight(), this.getHeight());

	}
}
