package api.simplelib.vars;

/**
 * @author ci010
 */
public abstract class VarRefBase<T> implements VarRef<T>
{
	protected final String name;

	public VarRefBase(String name)
	{
		this.name = name;
	}

	@Override
	public String getName()
	{
		return name;
	}

	@Override
	public T getIfPresent(T fallback)
	{
		return isPresent() ? get() : fallback;
	}

	@Override
	public T setIfAbsent(T value)
	{
		if (!isPresent())
			set(value);
		return get();
	}
}
