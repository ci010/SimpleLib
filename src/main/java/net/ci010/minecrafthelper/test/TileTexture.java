package net.ci010.minecrafthelper.test;

import net.minecraft.util.ResourceLocation;

/**
 * @author ci010
 */
public class TileTexture implements TextureInfo
{
	private ResourceLocation location;
	private int u, v, width, height;

	public TileTexture(ResourceLocation location, int u, int v, int width, int height)
	{
		this.location = location;
		this.u = u;
		this.v = v;
		this.width = width;
		this.height = height;
	}
	@Override
	public ResourceLocation getTexture()
	{
		return location;
	}

	@Override
	public int getU()
	{
		return u;
	}

	@Override
	public int getV()
	{
		return v;
	}

	@Override
	public int getWidth()
	{
		return width;
	}

	@Override
	public int getHeight()
	{
		return height;
	}
}
