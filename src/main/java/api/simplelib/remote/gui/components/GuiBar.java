package api.simplelib.remote.gui.components;

import api.simplelib.remote.gui.ComponentAPI;
import api.simplelib.utils.TextureInfo;
import api.simplelib.vars.VarSync;

/**
 * The bar-like Component.
 * The effect of this is like the progress bar in furnace.
 *
 * @author ci010
 */
public class GuiBar extends GuiComponent
{
	public GuiBar(VarSync<Float> target, TextureInfo bar, int x, int y)
	{
		this.setPosRelative(x, y);
		this.transform.setSize(bar.getWidth(), bar.getHeight());
		this.getProperties().put(ComponentAPI.PROP_TEXTURE, bar);
		this.getProperties().using(ComponentAPI.PROP_PROGRESS, target);
	}

	/**
	 * @param direction The direction of the bar will move.
	 * @return this
	 */
	public GuiBar setDirection(Direction direction)
	{
		this.getProperties().put(ComponentAPI.PROP_DIRECTION, direction);
		return this;
	}

	public enum Direction
	{
		RIGHT, LEFT, UP, DOWN
	}
}
