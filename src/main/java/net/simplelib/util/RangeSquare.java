package net.simplelib.util;

/**
 * @author ci010
 */
public class RangeSquare
{
	Range x, y;

	public RangeSquare(Range x, Range y)
	{
		this.x = x;
		this.y = y;
	}

	public boolean isInside(int x, int y)
	{
		return this.x.include(x) && this.y.include(y);
	}
}
