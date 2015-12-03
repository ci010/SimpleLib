package net.ci010.minecrafthelper.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

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
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture.getTexture());
		this.drawTexturedModalRect(x, y, texture.getU(), texture.getV(), texture.getWidth(), texture.getHeight());
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
