package net.simplelib.recipe;

import com.google.common.base.Predicate;

import javax.annotation.Nullable;

/**
 * @author ci010
 */
public class TickCondition<T> implements Predicate<T>
{
	private int current, max;

	public TickCondition(int time)
	{
		this.max = time;
		current = 0;
	}

	@Override
	public boolean apply(@Nullable T input)
	{
		if (++current >= max)
		{
			current = 0;
			return true;
		}
		return false;
	}
}
