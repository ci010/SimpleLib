package test;

import api.simplelib.common.ModHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.client.model.TRSRTransformation;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;

/**
 * @author ci010
 */
@ModHandler
public class GuiTest extends Gui
{
	ResourceLocation inv = new ResourceLocation("textures/gui/container/inventory.png");

	float delta = (1 / 30f);
	private float v = 0;

	@SubscribeEvent
	public void onInit(GuiScreenEvent.InitGuiEvent.Pre event)
	{
		if (event.gui instanceof GuiInventory)
			this.v = 0;
	}

	@SubscribeEvent
	public void onRender(GuiScreenEvent.DrawScreenEvent.Pre event)
	{
		if (event.gui instanceof GuiInventory)
		{
			Minecraft.getMinecraft().getTextureManager().bindTexture(inv);
			if (v < 1)
			{
				GlStateManager.enableBlend();
				GlStateManager.enableAlpha();
				GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_ONE_MINUS_SRC_ALPHA);
				GlStateManager.color(v += delta, v, v, v);
			}
//			this.drawTexturedModalRect(0, 0, 0, 0, 176, 166);
//			event.setCanceled(true);
		}
	}

//	@SubscribeEvent
//	public void onRenderEffect(RenderEnt)

	public void startFade(int x, int y, int xSize, int ySize, int time)
	{
		Minecraft.getMinecraft().getTextureManager().bindTexture(null);
		Tessellator tessellator = Tessellator.getInstance();
		GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, GL11.GL_TEXTURE_ENV_MODE, GL11.GL_MODULATE);
		GlStateManager.enableBlend();
		GlStateManager.enableAlpha();
		GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GlStateManager.color(0.3f, 0.3f, 0.3f, 0.3f);

	}


	void test()
	{
		TRSRTransformation transformation = null;

		
	}
}
