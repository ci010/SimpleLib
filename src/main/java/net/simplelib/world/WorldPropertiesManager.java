package net.simplelib.world;

import api.simplelib.Instance;
import api.simplelib.registry.ModHandler;
import api.simplelib.utils.CapabilityUtils;
import api.simplelib.seril.ITagSerializable;
import com.google.common.collect.Maps;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.capabilities.CapabilityDispatcher;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Map;

/**
 * @author ci010
 */
@ModHandler
public class WorldPropertiesManager implements ITagSerializable
{
	private Map<Integer, CapabilityDispatcher> dimensions;
	public NBTTagCompound cache;

	@Instance
	private static WorldPropertiesManager instance = new WorldPropertiesManager();

	public static WorldPropertiesManager instance()
	{
		return instance;
	}

	private WorldPropertiesManager()
	{
		dimensions = Maps.newHashMapWithExpectedSize(DimensionManager.getIDs().length);
	}

	@SubscribeEvent
	public void onWorldLoad(WorldEvent.Load event)
	{
		CapabilityDispatcher dispatcher = CapabilityUtils.gatherCapabilities(new AttachWorldCapEvent(event.world));
		if (dispatcher != null)
		{
			int dimension = event.world.provider.getDimensionId();
			if (cache != null)
				dispatcher.deserializeNBT(cache.getCompoundTag(dimension + ""));
			dimensions.put(dimension, dispatcher);
		}
	}

	public ICapabilityProvider getWorldCap(int dimension)
	{
		return this.dimensions.get(dimension);
	}

	public ICapabilityProvider getWorldCap(World world)
	{
		return getWorldCap(world.provider.getDimensionId());
	}

	@Override
	public void readFromNBT(NBTTagCompound tag)
	{
		for (Map.Entry<Integer, CapabilityDispatcher> entry : dimensions.entrySet())
			entry.getValue().deserializeNBT(tag.getCompoundTag(entry.getKey().toString()));
	}

	@Override
	public void writeToNBT(NBTTagCompound tag)
	{
		for (Map.Entry<Integer, CapabilityDispatcher> entry : dimensions.entrySet())
			tag.setTag(entry.getKey().toString(), entry.getValue().serializeNBT());
	}
}
