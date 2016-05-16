package api.simplelib.gui.drawer;

import api.simplelib.gui.DrawNode;
import api.simplelib.gui.ComponentAPI;
import api.simplelib.gui.Properties;
import api.simplelib.utils.GuiUtil;
import api.simplelib.vars.VarOption;
import com.google.common.base.Optional;
import net.minecraft.client.gui.Gui;

/**
 * @author ci010
 */
public class DrawString extends Gui implements DrawNode
{
	public static final DrawString INSTANCE = new DrawString();

	private DrawString() {}

	@Override
	public void draw(int x, int y, Properties properties)
	{
		VarOption<String> property = properties.property(ComponentAPI.PROP_STRING);
		if (property.isPresent())
			this.drawString(GuiUtil.font(), property.get(), x, y, 0);
	}
}
