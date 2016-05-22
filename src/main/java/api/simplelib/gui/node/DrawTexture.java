package api.simplelib.gui.node;

import api.simplelib.Pipeline;
import api.simplelib.gui.ComponentAPI;
import api.simplelib.gui.Properties;
import api.simplelib.utils.TextureInfo;
import api.simplelib.utils.GuiUtil;
import api.simplelib.vars.VarForward;
import net.minecraft.client.gui.Gui;

/**
 * @author ci010
 */
public class DrawTexture extends Gui implements DrawNode
{
	public static final DrawTexture INSTANCE = new DrawTexture();

	private DrawTexture() {}

	@Override
	public void draw(int x, int y, Pipeline<DrawNode> pipeline, Properties properties)
	{
		VarForward<TextureInfo> property = properties.property(ComponentAPI.PROP_TEXTURE);
		if (property.isPresent())
		{
			TextureInfo texture = property.get();
			GuiUtil.bindToTexture(texture);
			this.drawTexturedModalRect(x, y,
					texture.getU(), texture.getV(), texture.getHeight(), texture.getHeight());
		}
	}
}
