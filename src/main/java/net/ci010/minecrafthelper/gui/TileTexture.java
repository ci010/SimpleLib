package net.ci010.minecrafthelper.gui;

/**
 * @author ci010
 */
public class TileTexture extends GuiComponent
{
	private TextureInfo texture;

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
