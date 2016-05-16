package api.simplelib.gui.components;

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
	protected Object key;
	protected int width = 0, height;

	public GuiString(Object key, int x, int y)
	{
		this.key = key;
		this.setPos(x, y);
		this.height = 8;
	}

	@Override
	public int getWidth()
	{
		return width;
	}

	@Override
	public int getHeight()
	{
		return height;
	}

	public void onLoad(Container container)
	{
		String content = key.toString();
		int width;
		if (this.width != (width = GuiUtil.font().getStringWidth(content)))
		{
			this.width = width;
		}
	}
}
