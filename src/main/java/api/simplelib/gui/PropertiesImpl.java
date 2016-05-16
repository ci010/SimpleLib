package api.simplelib.gui;

import api.simplelib.vars.VarBase;
import api.simplelib.vars.VarNotify;
import api.simplelib.vars.VarOption;
import com.google.common.collect.Maps;

import java.util.List;
import java.util.Map;

/**
 * @author ci010
 */
public class PropertiesImpl implements Properties
{
	private Map<Key, Object> data;
	private Map<String, Object> cache;

	@Override
	public <T> VarOption<T> property(Key<T> location)
	{
		return null;
	}

	@Override
	public <T> VarOption<T> cache(String id)
	{
		return null;
	}

	@Override
	public void clearCache()
	{

	}

	@Override
	public List<String> allCaches()
	{
		return null;
	}

	@Override
	public List<Key> allProperties()
	{
		return null;
	}

	class VarOptionBase<T> extends VarBase<T> implements VarOption<T>
	{
		@Override
		public boolean isPresent()
		{
			return this.data != null;
		}

		@Override
		public void present(VarNotify<T> var)
		{

		}

		@Override
		public void onChange(T t)
		{

		}
	}

	public PropertiesImpl()
	{
		this.data = Maps.newHashMap();
		this.cache = Maps.newConcurrentMap();
	}
}
