package net.simplelib.common;

/**
 * @author ci010
 */
public class RegionalCache<Position>
{
	private float[][] cache;
	private int radius, diameter;

	private OffsetMapping<Position> mapping;
	private Position center;
	private ValueDelegate<Position> delegate;

	public RegionalCache(int radius, OffsetMapping<Position> mapping, ValueDelegate<Position> delegate)
	{
		this.diameter = radius * 2 + 1;
		this.cache = new float[diameter][diameter];
		this.mapping = mapping;
		this.delegate = delegate;
		this.radius = radius;
	}

	public float getCenter()
	{
		return this.cache[radius][radius];//TODO check this
	}

	public float[][] getCache()
	{
		float[][] temp = new float[this.diameter][this.diameter];
		System.arraycopy(cache, 0, temp, 0, this.diameter);
		return temp;
	}

	public void moveTo(Position pos)
	{
		int xOffset = mapping.getXOffset(this.center, pos);
		int zOffset = mapping.getZOffset(this.center, pos);
		this.center = pos;
		this.move(xOffset, zOffset);
	}

	private void move(int xOffset, int zOffset)
	{
		if (xOffset > diameter || zOffset > diameter)
			this.refresh();
		else
		{
			float[][] temp = new float[diameter][diameter];
			int range = this.diameter - xOffset;
			if (xOffset < 0)
				for (int x = 0; x < range; x++)
					if (zOffset >= 0)
						System.arraycopy(this.cache[x], 0, temp[x - xOffset], zOffset, diameter - zOffset);
					else
						System.arraycopy(this.cache[x], -zOffset, temp[x - xOffset], 0, diameter + zOffset);
			else if (xOffset >= 0)
				for (int x = xOffset; x < this.diameter; x++)
					if (zOffset >= 0)
						System.arraycopy(this.cache[x], 0, temp[x - xOffset], zOffset, diameter - zOffset);
					else
						System.arraycopy(this.cache[x], -zOffset, temp[x - xOffset], 0, diameter + zOffset);
			for (int x = 0; x < this.diameter; x++)
				for (int z = 0; z < this.diameter; z++)
				{
					float v = temp[x][z];
					if (v == 0.0F)
						temp[x][z] = this.delegate.calculate(
								this.mapping.getPosFromOffset(this.center, x - this.radius, z - this.radius));
				}
			this.cache = temp;
		}
	}

	public void refresh()
	{
		for (int x = 0; x < this.diameter; x++)
			for (int z = 0; z < this.diameter; z++)
				this.delegate.calculate(this.mapping.getPosFromOffset(this.center, x - radius, z - radius));
	}

	public interface ValueDelegate<T>
	{
		float calculate(T pos);
	}

	public interface OffsetMapping<T>
	{
		int getXOffset(T center, T other);

		int getZOffset(T center, T other);

		T getPosFromOffset(T pos, int offsetX, int offsetZ);
	}
}
