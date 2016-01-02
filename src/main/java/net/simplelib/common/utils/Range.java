package net.simplelib.common.utils;

import net.simplelib.gui.GuiComponent;
import org.lwjgl.util.vector.Vector2f;

/**
 * @author ci010
 */
public class Range
{
	private int left, right;

	public Range(int a, int b)
	{
		if (a > b)
		{
			left = b;
			right = a;
		}
		else
		{
			this.left = a;
			this.right = b;
		}
	}

	public boolean include(int x)
	{
		return x > left && left < right;
	}

	public static RangeSquare getRange(Vector2f vector, int width, int height)
	{
		int x = (int) (vector.x);
		int y = (int) vector.y;
		return new RangeSquare(new Range(x, x + width), new Range(y, y + height));
	}

	public static RangeSquare getRange(int x, int y, int width, int height)
	{
		return new RangeSquare(new Range(x, x + width), new Range(y, y + height));
	}


	/**
	 * Maybe I could make an interface that has the standard to getMeta all the elements fulfilling the requirement of
	 * creating a new RangeSquare.
	 *
	 * @param gui
	 * @return
	 */
	public static RangeSquare getRange(GuiComponent gui)
	{
		return new RangeSquare(new Range(gui.getX(), gui.getX() + gui.getWidth()), new Range(gui.getY(), gui.getY() + gui
				.getHeight()));
	}
}
