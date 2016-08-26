package api.simplelib.ui.node;

import api.simplelib.ui.ElementAPI;
import api.simplelib.ui.elements.Element;
import api.simplelib.utils.GuiUtil;
import api.simplelib.utils.TextureInfo;
import api.simplelib.vars.VarRef;
import net.minecraft.client.gui.Gui;

import javax.annotation.Nonnull;

/**
 * @author ci010
 */
public class DrawTexture extends Gui implements DrawNode
{
	public static final DrawTexture INSTANCE = new DrawTexture();

	private DrawTexture() {}

	@Override
	public boolean apply(@Nonnull Element component)
	{
		VarRef<TextureInfo> var = component.getProperties().var(ElementAPI.LOC_DRAW_TEXTURE, ElementAPI.TEXTURE);
		if (!var.isPresent())
			return true;
		int x = component.transform().x, y = component.transform().y;
		TextureInfo texture = var.get();
		GuiUtil.bindTexture(texture);
		GuiUtil.drawTextureAt(x, y, texture);
		return false;
	}
}
