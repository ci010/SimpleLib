package net.simplelib.data;

import com.google.common.collect.Sets;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.simplelib.abstracts.NBTSeril;

import java.util.Set;

/**
 * @author ci010
 */
public class VarSync<T extends NBTSeril> extends Var<T>
{
	public final Side side = FMLCommonHandler.instance().getSide();
	private Set<Listener> listeners;

	public void addListener(Listener listener)
	{
		if (listeners == null)
			listeners = Sets.newHashSet();
		listeners.add(listener);
	}

	public void removeListener(Listener listener)
	{
		if (listeners != null)
			listeners.remove(listener);
	}

	public void setData(T data)
	{
		super.setData(data);
		if (listeners != null)
			for (Listener listener : listeners)
				listener.onChanged(this);
	}

	public interface Listener
	{
		void onChanged(VarSync var);
	}

	@Override
	public T getData()
	{
		return super.getData();
	}
}
