package net.ci010.minecrafthelper.deprecated;

import com.google.common.collect.Lists;
import net.minecraft.util.Vec3i;

import java.util.Collections;
import java.util.List;

/**
 * This class can load a 3D pattern and determine if they are connected.
 *
 * @author ci010
 */
public abstract class Pattern3D<T extends Vec3i>
{
	protected List<T> sub;

	/**
	 * @return if they list of block is connected.
	 */
	public boolean connected()
	{
		return this.connected(sub, Lists.<T>newArrayList(), this.getOrigin(), 1, 1);
	}

	private boolean connected(List<T> sub, List<T> done, T current, int direction, int
			original)
	{
		if (!done.contains(current))
			done.add(current);
		T next = this.nextPosition(current, direction);
		if (sub.contains(next) && !done.contains(next))
			this.connected(sub, done, next, direction, direction);
		int nextDirection = (direction + 1) % 6;
		if ((nextDirection = nextDirection == 0 ? 6 : nextDirection) == original)
			return true;
		this.connected(sub, done, current, nextDirection, original);
		return done.size() == sub.size();
	}

	/**
	 * @param pos       The current position.
	 * @param direction The current pointing direction in integer from 1 to 6.
	 *                  <p>1 is north/front, 2 is east/right, 3 is south/back, 4 is west/left side of current position.
	 *                  <p>5 is up, 6 is down side of current position.
	 * @return The next position that the direction pointing.
	 */
	public abstract T nextPosition(T pos, int direction);

	/**
	 * @return The origin of these vectors.
	 */
	public abstract T getOrigin();

	public Pattern3D(List<T> pos)
	{
		this.sub = pos;
		Collections.sort(this.sub);
		if (!this.connected())
			throw new IllegalArgumentException("The pattern is not continued!");
	}
}
