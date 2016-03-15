package api.simplelib.gui;

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
	public void draw()
	{
		this.drawTexture(texture);
	}

	@Override
	public int getWidth()
	{
		return texture.getWidth();
	}

	@Override
	public int getHeight()
	{
		return texture.getHeight();
	}
}
