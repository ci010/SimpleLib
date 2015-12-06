package net.ci010.minecrafthelper.util;

import net.ci010.minecrafthelper.gui.TextureInfo;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;

/**
 * @author ci010
 */
public class GuiUtil
{
	public static FontRenderer font()
	{
		return mc.fontRendererObj;
	}

	public static final Minecraft mc = Minecraft.getMinecraft();

	public static final TextureManager texture = mc.getTextureManager();

	public static final TextureInfo slot = new TextureInfo(
			new ResourceLocation("textures/gui/container/inventory.png"), 7, 141, 18, 18);

	public static void bindToTexture(TextureInfo info)
	{
		Minecraft.getMinecraft().getTextureManager().bindTexture(info.getTexture());
	}
}
