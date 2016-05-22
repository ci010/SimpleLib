package api.simplelib.gui.node;

import api.simplelib.Pipeline;
import api.simplelib.gui.ComponentAPI;
import api.simplelib.gui.Properties;
import api.simplelib.utils.GuiUtil;
import api.simplelib.vars.VarForward;
import net.minecraft.client.gui.Gui;

/**
 * @author ci010
 */
public class DrawString extends Gui implements DrawNode
{
	public static final DrawString INSTANCE = new DrawString();

	private DrawString() {}

	@Override
	public void draw(int x, int y, Pipeline<DrawNode> pipeline, Properties properties)
	{
		VarForward<CharSequence> property = properties.property(ComponentAPI.PROP_STRING);
		if (property.isPresent())
			this.drawString(GuiUtil.font(), property.get().toString(), x, y, 0);
	}
}
