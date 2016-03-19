package net.simplelib.common;

import net.minecraft.util.ITickable;
import net.minecraftforge.fml.common.FMLCommonHandler;

import java.util.WeakHashMap;

/**
 * @author ci010
 */
public enum TickServerSide implements ITickable
{
	INSTANCE;

	private WeakHashMap<Object, ITickable> updateWeakHashMap = new WeakHashMap<Object, ITickable>();

	public void put(Object key, ITickable tickable)
	{
		updateWeakHashMap.put(key, tickable);
	}

	public ITickable remove(Object key)
	{
		return updateWeakHashMap.remove(key);
	}

	@Override
	public void update()
	{
		for (ITickable tickable : updateWeakHashMap.values())
			tickable.update();
	}
}
