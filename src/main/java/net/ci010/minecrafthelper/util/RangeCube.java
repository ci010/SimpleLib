package net.ci010.minecrafthelper.util;

/**
 * @author ci010
 */
public class RangeCube
{
	Range x, y, z;

	public RangeCube(Range x, Range y, Range z)
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
