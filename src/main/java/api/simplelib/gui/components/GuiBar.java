package api.simplelib.gui.components;

import api.simplelib.gui.ComponentAPI;
import api.simplelib.utils.TextureInfo;
import api.simplelib.vars.Var;
import com.google.common.base.Supplier;

/**
 * The bar-like Component.
 * The effect of this is like the progress bar in furnace.
 *
 * @author ci010
 */
public class GuiBar extends GuiComponent
{
	public GuiBar(Var<Float> target, TextureInfo bar, int x, int y)
	{
		this.setPos(x, y);
		this.setSize(bar.getWidth(), bar.getHeight());
		this.getProperties().property(ComponentAPI.PROP_TEXTURE).set(bar);
		this.getProperties().property(ComponentAPI.PROP_PROGRESS).delegate(target);
	}

	/**
	 * @param direction The direction of the bar will move.
	 * @return this
	 */
	public GuiBar setDirection(Direction direction)
	{
		this.getProperties().property(ComponentAPI.PROP_DIRECTION).set(direction);
		return this;
	}

	public enum Direction
	{
		RIGHT, LEFT, UP, DOWN;
	}
}
