package net.simplelib.gui;

import api.simplelib.gui.Properties;
import api.simplelib.utils.TypeUtils;
import api.simplelib.vars.Var;
import api.simplelib.vars.VarBase;
import api.simplelib.vars.VarForward;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author ci010
 */
public class PropertiesImpl implements Properties
{
	private Map<Key, VarForward> data;
	private Map<String, Object> cache;

	@Override
	public <T> VarForward<T> property(Key<T> location)
	{
		if (data.containsKey(location))
			return data.get(location);
		VarForwardable<T> var = new VarForwardable<T>();
		data.put(location, var);
		return var;
	}

	@Override
	public <T> T getCache(String id)
	{
		return TypeUtils.cast(cache.get(id));
	}

	@Override
	public void putCache(String id, Object cache)
	{
		this.cache.put(id, cache);
	}

	@Override
	public void clearCache()
	{
		cache.clear();
	}

	@Override
	public Set<String> allCaches()
	{
		return cache.keySet();
	}

	@Override
	public Set<Key> allProperties()
	{
		Set<Key> set = Sets.newHashSet();
		List<Key> removed = Lists.newArrayListWithCapacity(data.size());
		for (Key key : data.keySet())
			if (!this.property(key).isPresent())
				set.add(key);
			else
				removed.add(key);
		for (Key key : removed)
			data.remove(key);
		return data.keySet();
	}

	class VarForwardable<T> implements VarForward<T>
	{
		private Var<T> delegate;

		@Override
		public boolean isPresent()
		{
			return delegate == null;
		}

		@Override
		public void delegate(Var<T> var)
		{
			if (data != null)
				this.delegate = var;
		}

		@Override
		public Var<T> delegate()
		{
			return delegate;
		}

		@Override
		public void set(T value)
		{
			if (this.delegate != null)
				delegate.set(value);
			else
				delegate = new VarBase<T>(value);
		}

		@Override
		public T get()
		{
			return delegate == null ? null : delegate.get();
		}

		@Override
		public boolean equals(Object o)
		{
			if (this == o) return true;
			if (o == null) return false;
			if (o instanceof Var)
				return delegate.equals(((Var<?>) o).get());
			else return super.equals(o);
		}

		@Override
		public int hashCode()
		{
			return delegate != null ? delegate.hashCode() : 0;
		}
	}

	public PropertiesImpl()
	{
		this.data = Maps.newHashMap();
		this.cache = Maps.newConcurrentMap();
	}
}
