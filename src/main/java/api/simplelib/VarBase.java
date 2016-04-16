package api.simplelib;

/**
 * @author ci010
 */
public class VarBase<T> implements Var<T>
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
