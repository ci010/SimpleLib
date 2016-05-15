package api.simplelib.vars;

import api.simplelib.Callback;
import com.google.common.collect.Sets;

import java.util.Iterator;
import java.util.Set;

/**
 * @author ci010
 */
public abstract class VarNotifyBase<T> extends VarBase<T> implements VarNotify<T>
{
	protected Set<Callback<VarNotify<T>>> listeners;

	protected void load(T data)
	{
		super.set(data);
	}

	public void set(T data)
	{
		super.set(data);
		if (!data.equals(this.get()))
			if (listeners != null)
				for (Callback<VarNotify<T>> listener : listeners)
					listener.onChange(this);
	}

	@Override
	public void add(Callback<VarNotify<T>> callBack)
	{
		if (listeners == null)
			listeners = Sets.newHashSet();
		listeners.add(callBack);
	}

	@Override
	public void remove(Callback<VarNotify<T>> callBack)
	{
		if (listeners != null)
			listeners.remove(callBack);
	}

	@Override
	public Iterator<Callback<VarNotify<T>>> iterator()
	{
		return listeners.iterator();
	}
}
