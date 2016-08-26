package api.simplelib.vars;

/**
 * @author ci010
 */
public class VarRefPlain<T> extends VarBase<T> implements VarRef<T>
{
	private String name;

	public VarRefPlain(String name, T value)
	{
		this.name = name;
		this.data = value;
	}

	@Override
	public boolean isPresent()
	{
		return data != null;
	}

	@Override
	public String getName()
	{
		return name;
	}

	@Override
	public T getIfPresent(T fallback)
	{
		return this.data == null ? fallback : data;
	}

	@Override
	public T setIfAbsent(T value)
	{
		if (data == null)
			data = value;
		return data;
	}
}
