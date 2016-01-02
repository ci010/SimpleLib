package net.simplelib.common;

/**
 * @author ci010
 */
public class Var<T>
{
	private T data;

	public T get()
	{
		return data;
	}

	protected void set(T data)
	{
		this.data = data;
	}
}
