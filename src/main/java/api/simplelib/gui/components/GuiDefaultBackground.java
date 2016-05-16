package api.simplelib.gui.components;

import api.simplelib.gui.ComponentAPI;
import org.apache.commons.lang3.tuple.Pair;

/**
 * @author ci010
 */
public class GuiDefaultBackground extends GuiComponent
{
	public GuiDefaultBackground(int x, int y, int width, int height)
	{
		this.getDrawPipe().addLast(ComponentAPI.DRAW_BACKGROUND);
		this.getProperties().property(ComponentAPI.PROP_BACK_SIZE).set(Pair.of(width, height));
		this.x = x;
		this.y = y;
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
}
