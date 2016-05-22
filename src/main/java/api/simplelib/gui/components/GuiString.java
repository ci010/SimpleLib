package api.simplelib.gui.components;

import api.simplelib.gui.ComponentAPI;
import api.simplelib.utils.StringSource;
import net.minecraft.inventory.Container;
import api.simplelib.utils.GuiUtil;

/**
 * One of the most basic components in gui.
 * This just simply draw a string.
 *
 * @author ci010
 */
public class GuiString extends GuiComponent
{
	protected CharSequence key;

	public GuiString(CharSequence key, int x, int y)
	{
		this.key = key;
		if (key instanceof StringSource)
			this.getProperties().property(ComponentAPI.PROP_STRING_SRC).set(((StringSource) key).source().getSource());
		this.getProperties().property(ComponentAPI.PROP_STRING).set(key);
		this.setPos(x, y);
		this.height = 8;
	}

	public void onLoad(Container container)
	{
		String content = key.toString();
		int width;
		if (this.width != (width = GuiUtil.font().getStringWidth(content)))
			this.width = width;
		this.width = width;
	}
}
