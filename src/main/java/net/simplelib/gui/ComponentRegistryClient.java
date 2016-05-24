package net.simplelib.gui;

import api.simplelib.Pipeline;
import api.simplelib.gui.ComponentAPI;
import api.simplelib.gui.ComponentRepository;
import api.simplelib.gui.Properties;
import api.simplelib.gui.node.*;
import api.simplelib.registry.ModProxy;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import org.lwjgl.opengl.GL11;

/**
 * @author ci010
 */
@ModProxy(side = Side.CLIENT, genericType = ComponentRepository.class)
public class ComponentRegistryClient extends ComponentRegistryCommon
{
	@Override
	protected void register()
	{
		registerDrawNode(ComponentAPI.LOC_DRAW_TEXTURE, DrawTexture.INSTANCE);
		registerDrawNode(ComponentAPI.LOC_DRAW_BACKGROUND, DrawerDefaultBackground.INSTANCE);
		registerDrawNode(ComponentAPI.LOC_DRAW_BORDER_TEXTS, DrawBorderTexts.INSTANCE);
		registerDrawNode(ComponentAPI.LOC_DRAW_STRING, DrawString.INSTANCE);

		registerDrawNode(ComponentAPI.LOC_ANIM_FADE_IN, AnimationFadeOut.INSTANCE);
		registerDrawNode(ComponentAPI.LOC_ANIM_FADE_IN, AnimationFadeIn.INSTANCE);

		registerDrawNode(new ResourceLocation("draw:pre"), new DrawNode()
		{
			@Override
			public void draw(int x, int y, Pipeline<DrawNode> pipeline, Properties properties)
			{
				GL11.glPushMatrix();
			}
		});
		registerDrawNode(new ResourceLocation("draw:post"), new DrawNode()
		{
			@Override
			public void draw(int x, int y, Pipeline<DrawNode> pipeline, Properties properties)
			{
				GL11.glPopMatrix();
			}
		});
	}
}
