package api.simplelib.ui.node;

import api.simplelib.ui.ElementState;
import api.simplelib.ui.elements.Element;
import api.simplelib.vars.VarRef;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

/**
 * @author ci010
 */
public class DrawMCButton extends Gui implements DrawNode
{
	@Override
	public boolean apply(@Nonnull Element component)
	{
		Minecraft mc = Minecraft.getMinecraft();
		FontRenderer fontrenderer = mc.fontRendererObj;
		mc.getTextureManager().bindTexture(new ResourceLocation("textures/gui/widgets.png"));
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		ElementState state = component.state;
		String string = "";
		VarRef<String> str = component.getProperties().str("button:content");
		if (str.isPresent())
			string = str.get();

		int factor = 0;
		int color = 14737632;

		switch(state)
		{
			case HOVER:
				factor = 2;
				color = 16777120;
				break;
			case NORMAL:
				factor = 1;
				color = 10526880;
				break;
		}
		int x = component.transform().x, y = component.transform().y;
		GlStateManager.enableBlend();
		GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
		GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
		this.drawTexturedModalRect(x, y, 0, 46 + factor * 20,
				component.transform().width / 2, component.transform().height);
		this.drawTexturedModalRect(x + y / 2, y,
				200 - y / 2, 46 + factor * 20, component.transform().width / 2,
				component.transform().height);

		this.drawCenteredString(fontrenderer, string, x + component.transform().width / 2,
				component.transform().width + (component.transform().height - 8) / 2, color);
		return false;
	}
}
