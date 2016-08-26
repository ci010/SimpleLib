package api.simplelib.vars;

/**
 * @author ci010
 */
public class VarRefDelegate<T> extends VarRefBase<T> implements VarRef<T>
{
	private Var<T> delegate;

	public VarRefDelegate(String name)
	{
		super(name);
	}

	public VarRefDelegate(String name, Var<T> var)
	{
		this(name);
		this.delegate = var;
	}

	public boolean isPresent()
	{
		return delegate != null;
	}

	@Override
	public void set(T value)
	{
		if (delegate == null) return;
		delegate.set(value);
	}

	@Override
	public T get()
	{
		return delegate != null ? delegate.get() : null;
	}
}
