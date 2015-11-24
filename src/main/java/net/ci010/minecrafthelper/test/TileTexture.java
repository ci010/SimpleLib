package net.ci010.minecrafthelper.test;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.ResourceLocation;

/**
 * @author ci010
 */
public class TileTexture extends Gui implements GuiComponent
{
	private TextureInfo texture;
	private int x, y;

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
}
