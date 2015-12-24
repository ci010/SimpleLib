package net.simplelib.util;

import com.google.common.collect.Lists;

import java.util.Collections;
import java.util.List;

/**
 * @author ci010
 */
public class Pattern3D
{
	protected List<Vector> sub;

	/**
	 * @return if they list of block is connected.
	 */
	public boolean connected()
	{
		return this.connected(sub, Lists.<Vector>newArrayList(), sub.get(0), 1, 1);
	}

	private boolean connected(List<Vector> sub, List<Vector> done, Vector current, int direction, int
			original)
	{
		if (!done.contains(current))
			done.add(current);
		Vector next = null;
		switch (direction)
		{
			case 1:
				next = current.offset(0, 0, 1);
				break;
			case 2:
				next = current.offset(1, 0, 0);
				break;
			case 3:
				next = current.offset(0, 0, -1);
				break;
			case 4:
				next = current.offset(-1, 0, 0);
				break;
			case 5:
				next = current.offset(0, 1, 0);
				break;
			case 6:
				next = current.offset(0, -1, 0);
		}
		if (sub.contains(next) && !done.contains(next))
			this.connected(sub, done, next, direction, direction);
		int nextDirection = (direction + 1) % 6;
		if ((nextDirection = nextDirection == 0 ? 6 : nextDirection) == original)
			return true;
		this.connected(sub, done, current, nextDirection, original);
		return done.size() == sub.size();
	}

	public Pattern3D(List<Vector> pos)
	{
		this.sub = pos;
		Collections.sort(this.sub);
		if (!this.connected())
			throw new IllegalArgumentException("The pattern is not continued!");
	}

	/**
	 * Transfer the pattern to a specific coordination.
	 *
	 * @param origin The block position will be transfer to.
	 * @return The actual block positions.
	 */
	public List<Vector> transferTo(Vector origin)
	{
		List<Vector> result = Lists.newArrayList();
		for (Vector pos : sub)
			result.add(pos.offset(origin.getX(), origin.getY(), origin.getZ()));
		return result;
	}

	public interface Vector extends Comparable<Vector>
	{
		int getX();

		int getY();

		int getZ();

		/**
		 * Get a new vector that has the offset to current vector.
		 *
		 * @param x The offset in x coordination.
		 * @param y The offset in y coordination.
		 * @param z The offset in z coordination.
		 * @return new vector that has a certain offset to current vector.
		 */
		Vector offset(int x, int y, int z);
	}
}
