package api.simplelib.vars;

import api.simplelib.utils.TypeUtils;

import java.util.Map;

/**
 * @author ci010
 */
public class VarRefBuilder<T> implements VarRef.Delegate<T>, VarRef.Mapping<T>
{
	private String name;
	private Var<T> delegate;
	private Map<String, T> map;
	private T plain;

	public VarRefBuilder(String name)
	{
		this.name = name;
	}

	@Override
	public boolean isPresent()
	{
		return delegate != null || map != null;
	}

	@Override
	public String getName()
	{
		return name;
	}

	@Override
	public T getIfPresent(T fallback) {
		return null;
	}

	@Override
	public T setIfAbsent(T value) {
		return null;
	}

	@Override
	public void set(T value)
	{
		if (delegate != null)
			delegate.set(value);
		else if (map != null)
			map.put(name, value);
		else if (plain == null)
			plain = value;
	}

	@Override
	public T get()
	{
		return (plain != null) ? plain : delegate != null ? delegate.get() : map != null ? map.get(name) : null;
	}

	@Override
	public void setDelegate(Var<? extends T> var)
	{
		if (delegate == null)
			if (map == null && plain == null) delegate = TypeUtils.cast(var);
			else throw new IllegalArgumentException("Cannot set delegate as it already delegate to a map.");
	}

	@Override
	public Var<T> getDelegate()
	{
		return delegate;
	}

	@Override
	public void setMap(Map<String, T> map)
	{
		if (this.map == null)
			if (delegate == null && plain == null)
				this.map = map;
			else throw new IllegalArgumentException("Cannot set map delegate as it already delegate to a Var.");
	}

	@Override
	public Map<String, T> getMap()
	{
		return this.map;
	}

	public VarRef<T> toRef()
	{
		if (plain != null && map == null && delegate == null)
			return new VarRefPlain<T>(name, plain);
		if (delegate != null && map == null && plain == null)
			return new VarRefDelegate<T>(name, delegate);
		else if (delegate == null && map != null && plain == null)
			return new VarRefMapping<T>(name, map);
		else return null;
	}
}
