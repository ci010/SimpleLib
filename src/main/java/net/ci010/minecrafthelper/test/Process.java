package net.ci010.minecrafthelper.test;

/**
 * @author ci010
 */
public interface Process extends UpdateSafe
{
	void preUpdate();

	void postUpdate();

	class Var<T>
	{
		private T data;

		public T getData()
		{
			return data;
		}

		public void setData(T data)
		{
			this.data = data;
		}
	}
}
