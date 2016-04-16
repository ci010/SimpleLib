package net.simplelib.world;

import api.simplelib.common.Instance;
import api.simplelib.common.ModHandler;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import api.simplelib.utils.ITagSerializable;

import java.util.Map;

/**
 * @author ci010
 */
@ModHandler
public class WorldPropertiesManager implements ITagSerializable
{
	@Instance
	private static WorldPropertiesManager instnace;

	public static WorldPropertiesManager instance()
	{
		if (instnace == null)
			instnace = new WorldPropertiesManager();
		return instnace;
	}

	{
		Integer[] ids = DimensionManager.getStaticDimensionIDs();
		ImmutableMap.Builder<Integer, Map<String, IExtendedWorldProperties>> builder = ImmutableMap.builder();
		for (Integer id : ids)
			builder.put(id, Maps.<String, IExtendedWorldProperties>newHashMap());
		dimensions = builder.build();
	}

	private ImmutableMap<Integer, Map<String, IExtendedWorldProperties>> dimensions;

	public IExtendedWorldProperties getProperties(int dimension, String id)
	{
		return dimensions.get(dimension).get(id);
	}

	public void register(int dimension, String id, IExtendedWorldProperties properties)
	{
		Map<String, IExtendedWorldProperties> map = dimensions.get(dimension);
		map.put(id, properties);
	}

	@SubscribeEvent
	public void onWorldLoad(WorldEvent.Load event)
	{
		for (IExtendedWorldProperties properties : dimensions.get(event.world.provider.getDimensionId()).values())
			properties.load(event.world);
	}

	@SubscribeEvent
	public void onWorldUnload(WorldEvent.Unload event)
	{
		for (IExtendedWorldProperties properties : dimensions.get(event.world.provider.getDimensionId()).values())
			properties.unload(event.world);
	}

	@Override
	public void readFromNBT(NBTTagCompound tag)
	{
		for (Map.Entry<Integer, Map<String, IExtendedWorldProperties>> propertyEntry : dimensions.entrySet())
			if (tag.hasKey(propertyEntry.getKey().toString()))
			{
				NBTTagCompound dim = tag.getCompoundTag(propertyEntry.getKey().toString());
				for (Map.Entry<String, IExtendedWorldProperties> entry : propertyEntry.getValue().entrySet())
					if (dim.hasKey(entry.getKey()))
						entry.getValue().readFromNBT(tag.getCompoundTag(entry.getKey()));
			}
	}

	@Override
	public void writeToNBT(NBTTagCompound tag)
	{
		for (Map.Entry<Integer, Map<String, IExtendedWorldProperties>> propertyEntry : dimensions.entrySet())
		{
			NBTTagCompound dim = new NBTTagCompound();
			for (Map.Entry<String, IExtendedWorldProperties> entry : propertyEntry.getValue().entrySet())
			{
				NBTTagCompound compound = new NBTTagCompound();
				entry.getValue().writeToNBT(compound);
				dim.setTag(entry.getKey(), compound);
			}
			if (!dim.hasNoTags())
				tag.setTag(propertyEntry.getKey().toString(), dim);
		}

	}
}
