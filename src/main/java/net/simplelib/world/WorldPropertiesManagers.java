package net.simplelib.world;

import api.simplelib.Instance;
import api.simplelib.registry.ModHandler;
import api.simplelib.registry.ModProxy;
import api.simplelib.utils.CapabilityUtils;
import api.simplelib.utils.NBTTagBuilder;
import api.simplelib.world.AttachWorldCapEvent;
import api.simplelib.world.ChunkData;
import api.simplelib.world.WorldPropertiesManager;
import com.google.common.collect.Maps;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.capabilities.CapabilityDispatcher;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

import java.util.Map;

/**
 * @author ci010
 */
@ModHandler
@ModProxy(side = Side.SERVER, genericType = WorldPropertiesManager.class)
public class WorldPropertiesManagers implements WorldPropertiesManager
{
	private Map<Integer, CapabilityDispatcher> dimensions;

	@Instance
	private static WorldPropertiesManagers instance = new WorldPropertiesManagers();

	public static WorldPropertiesManagers instance()
	{
		return instance;
	}

	private WorldPropertiesManagers()
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
			String name = "world_cap_" + dimension;
			CapLoader loader = (CapLoader) event.world.loadItemData(CapLoader.class, name);
			loader.load(dispatcher);
			dimensions.put(dimension, dispatcher);
		}
	}

	@Override
	public ICapabilityProvider getCapabilityProvider(int dimension)
	{
		return this.dimensions.get(dimension);
	}

	@Override
	public ICapabilityProvider getCapabilityProvider(World world)
	{
		return getCapabilityProvider(world.provider.getDimensionId());
	}

	@Override
	public ChunkData getChunkData(World world, BlockPos pos)
	{
		return ChunkDataImpl.getChunkData(world, pos);
	}

	@Override
	public ChunkData getChunkData(World world, ChunkCoordIntPair pos)
	{
		return ChunkDataImpl.getChunkData(world, pos);
	}

	@Override
	public ChunkData getChunkData(Chunk chunk)
	{
		return ChunkDataImpl.getChunkData(chunk);
	}

	private class CapLoader extends WorldSavedData
	{
		CapabilityDispatcher dispatcher;
		NBTTagCompound cache;

		public CapLoader(String name)
		{
			super(name);
		}

		void load(CapabilityDispatcher dispatcher)
		{
			this.dispatcher = dispatcher;
			dispatcher.deserializeNBT(cache);
			cache = null;
		}

		@Override
		public void readFromNBT(NBTTagCompound nbt)
		{
			cache = NBTTagBuilder.newBuilder().copyFrom(nbt).build();
		}

		@Override
		public void writeToNBT(NBTTagCompound nbt)
		{
			if (dispatcher != null)
				NBTTagBuilder.newBuilder(dispatcher.serializeNBT()).copyTo(nbt);
		}
	}
}
