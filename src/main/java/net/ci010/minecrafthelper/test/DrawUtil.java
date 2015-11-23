package net.ci010.minecrafthelper.test;

import net.minecraft.client.Minecraft;

/**
 * @author ci010
 */
public class DrawUtil
{
	public static void bindToTexture(TextureInfo info)
	{
		Minecraft.getMinecraft().getTextureManager().bindTexture(info.getTexture());
	}
}
