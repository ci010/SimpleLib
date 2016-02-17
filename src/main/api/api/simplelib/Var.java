package api.simplelib;

/**
 * @author ci010
 */
public class Var<T> implements IVar<T>
{
	private T data;

	public T get()
	{
		return data;
	}

	public void set(T data)
	{
		this.data = data;
	}
}
