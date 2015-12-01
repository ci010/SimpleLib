package net.ci010.minecrafthelper.gui;

import net.minecraft.util.ResourceLocation;

/**
 * @author ci010
 */
public class TextureInfo
{
	private ResourceLocation location;
	private int u, v, width, height;

	public TextureInfo(ResourceLocation location, int u, int v, int width, int height)
	{
		this.location = location;
		this.u = u;
		this.v = v;
		this.width = width;
		this.height = height;
	}

	public ResourceLocation getTexture()
	{
		return location;
	}

	public int getU()
	{
		return u;
	}

	public int getV()
	{
		return v;
	}

	public int getWidth()
	{
		return width;
	}

	public int getHeight()
	{
		return height;
	}
}
