package net.ci010.minecrafthelper.util;

import com.google.common.collect.Lists;
import net.minecraft.util.Vec3i;

import java.util.Collections;
import java.util.List;

/**
 * @author ci010
 */
public abstract class Pattern3D<T extends Vec3i>
{
	protected List<T> sub;

	public boolean connected(List<T> blocks)
	{
		return this.connected(blocks, Lists.<T>newArrayList(), this.getOrigin(), 1, 1);
	}

	private boolean connected(List<T> blocks, List<T> done, T current, int direction, int
			original)
	{
		if (!done.contains(current))
			done.add(current);
		T next = this.nextBlock(current, direction);
		if (blocks.contains(next) && !done.contains(next))
			this.connected(blocks, done, next, direction, direction);
		int nextDirection = (direction + 1) % 6;
		if ((nextDirection = nextDirection == 0 ? 6 : nextDirection) == original)
			return true;
		this.connected(blocks, done, current, nextDirection, original);
		return done.size() == blocks.size();
	}

	public abstract T nextBlock(T pos, int direction);

	public abstract T getOrigin();

	public Pattern3D(List<T> pos)
	{
		Collections.sort(this.sub);
		if (this.connected(pos))
			this.sub = pos;
		else
			throw new IllegalArgumentException("The pattern is not continued!");
	}
}
