package net.simplelib.common;

/**
 * @author ci010
 */
public class RangeCube
{
	RangeBase x, y, z;

	public RangeCube(RangeBase x, RangeBase y, RangeBase z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public boolean isInside(int x, int y, int z)
	{
		return this.x.include(x) && this.y.include(y) && this.z.include(z);
	}
}
