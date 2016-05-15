package net.simplelib.common;

/**
 * @author ci010
 */
public class RangeSquare
{
	RangeBase xRange, yRange;

	public RangeSquare(RangeBase x, RangeBase y)
	{
		this.xRange = x;
		this.yRange = y;
	}

	public boolean isInside(int x, int y)
	{
		return this.xRange.include(x) && this.yRange.include(y);
	}
}
