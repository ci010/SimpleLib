package api.simplelib.remote.gui.components;

import api.simplelib.remote.gui.ComponentAPI;
import org.apache.commons.lang3.tuple.Pair;

/**
 * @author ci010
 */
public class GuiDefaultBackground extends GuiComponent
{
	public GuiDefaultBackground(int x, int y, int width, int height)
	{
		this.getDrawPipe().addLast(ComponentAPI.DRAW_BACKGROUND);
		this.getProperties().put(ComponentAPI.PROP_BACK_SIZE, Pair.of(width, height));
		this.transform.setPos(x, y);
	}
}
