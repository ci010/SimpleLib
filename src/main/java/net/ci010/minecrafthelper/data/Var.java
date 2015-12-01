package net.ci010.minecrafthelper.data;

/**
 * @author ci010
 */
class Var<T>
{
	private T data;

	public T getData()
	{
		return data;
	}

	protected void setData(T data)
	{
		this.data = data;
	}
}
