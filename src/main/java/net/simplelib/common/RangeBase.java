package net.simplelib.common;

import api.simplelib.gui.components.GuiComponent;
import org.lwjgl.util.vector.Vector2f;

/**
 * @author ci010
 */
public class RangeBase extends Vector2i
{
	public RangeBase(int a, int b)
	{
		super(a > b ? b : a, a > b ? a : b);
	}

	public boolean include(int x)
	{
		return x > getX() && x < getY();
	}

	public static RangeSquare getRange(Vector2f vector, int width, int height)
	{
		int x = (int) (vector.x);
		int y = (int) vector.y;
		return new RangeSquare(new RangeBase(x, x + width), new RangeBase(y, y + height));
	}

	public static RangeSquare getRange(int x, int y, int width, int height)
	{
		return new RangeSquare(new RangeBase(x, x + width), new RangeBase(y, y + height));
	}


	/**
	 * Maybe I could make an interface that has the standard to build all the elements fulfilling the requirement of
	 * creating a new RangeSquare.
	 *
	 * @param gui
	 * @return
	 */
	public static RangeSquare getRange(GuiComponent gui)
	{
		return new RangeSquare(new RangeBase(gui.getX(), gui.getX() + gui.getWidth()), new RangeBase(gui.getY(), gui.getY() + gui
				.getHeight()));
	}
}
