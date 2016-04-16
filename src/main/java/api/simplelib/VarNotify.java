package api.simplelib;

import api.simplelib.minecraft.Callback;
import com.google.common.collect.Sets;

import java.util.Iterator;
import java.util.Set;

/**
 * @author ci010
 */
public abstract class VarNotify<T> extends VarBase<T> implements Callback.Container<T>
{
	protected Set<Callback<T>> listeners;

	protected void load(T data)
	{
		super.set(data);
	}

	public void set(T data)
	{
		super.set(data);
		if (!data.equals(this.get()))
			if (listeners != null)
				for (Callback<T> listener : listeners)
					listener.onChange(data);
	}

	@Override
	public void add(Callback<T> callBack)
	{
		if (listeners == null)
			listeners = Sets.newHashSet();
		listeners.add(callBack);
	}

	@Override
	public void remove(Callback<T> callBack)
	{
		if (listeners != null)
			listeners.remove(callBack);
	}

	@Override
	public Iterator<Callback<T>> iterator()
	{
		return listeners.iterator();
	}
}
