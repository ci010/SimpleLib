package api.simplelib.gui.drawer;

import api.simplelib.gui.ComponentAPI;
import api.simplelib.gui.DrawNode;
import api.simplelib.gui.Properties;
import api.simplelib.utils.TextureInfo;
import api.simplelib.utils.GuiUtil;
import api.simplelib.vars.VarOption;
import net.minecraft.client.gui.Gui;

/**
 * @author ci010
 */
public class DrawTexture extends Gui implements DrawNode
{
	public static final DrawTexture INSTANCE = new DrawTexture();

	private DrawTexture() {}

	@Override
	public void draw(int x, int y, Properties properties)
	{
		VarOption<TextureInfo> property = properties.property(ComponentAPI.PROP_TEXTURE);
		if (property.isPresent())
		{
			TextureInfo texture = property.get();
			GuiUtil.bindToTexture(texture);
			this.drawTexturedModalRect(x, y,
					texture.getU(), texture.getV(), texture.getHeight(), texture.getHeight());
		}
	}
}
