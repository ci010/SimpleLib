package api.simplelib.vars;

import java.util.Map;

/**
 * @author ci010
 */
public class VarRefMapping<T> extends VarRefBase<T> implements VarRef<T>
{
	private Map<String, T> map;

	public VarRefMapping(String name, Map<String, T> map)
	{
		super(name);
		this.map = map;
	}

	@Override
	public boolean isPresent()
	{
		return map != null && map.get(getName()) != null;
	}

	@Override
	public void set(T value)
	{
		map.put(getName(), value);
	}

	@Override
	public T get()
	{
		return map.get(getName());
	}
}
