package net.simplelib.world.region;

import api.simplelib.registry.ModHandler;
import api.simplelib.utils.GenericUtil;
import com.google.common.base.Optional;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.event.world.ChunkDataEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.simplelib.world.AttachWorldCapEvent;

import java.util.concurrent.Callable;

/**
 * @author ci010
 */
@ModHandler
public class RegionHook
{
	@CapabilityInject(RegionManager.class)
	public static final Capability<RegionManager> REGION_MANAGER = null;

	//	@SubscribeEvent
	public void onWorldTick(TickEvent.WorldTickEvent event)
	{
		for (EntityPlayer playerEntity : event.world.playerEntities)
		{
			BlockPos position = playerEntity.getPosition();
//			Region region = RegionManager.getRegion(playerEntity.worldObj, position);
//			if (region != null)
//			{
//
//			}
		}
	}

	@SubscribeEvent
	public void onChunkSave(ChunkDataEvent.Save save)
	{
		Chunk chunk = save.getChunk();
		Optional<Region> regionOptional = RegionManager.getRegion(chunk.getWorld(), chunk.xPosition, chunk.zPosition);
		if (regionOptional.isPresent())
		{
			Region region = regionOptional.get();
			NBTTagCompound chunkData = save.getData();
			NBTTagCompound regionData = new NBTTagCompound();
			regionData.setString("id", region.getId());
			
			chunkData.setTag("RegionData", regionData);
		}
	}

	@SubscribeEvent
	public void onWorldCap(AttachWorldCapEvent event)
	{
		final World world = event.getWorld();
		event.addCapability(new ResourceLocation("helper", "region"), new ICapabilitySerializable<NBTTagCompound>()
		{
			public RegionManager manager = new RegionManager(world);

			@Override
			public NBTTagCompound serializeNBT()
			{
				NBTTagCompound tag = new NBTTagCompound();
				manager.writeToNBT(tag);
				return tag;
			}

			@Override
			public void deserializeNBT(NBTTagCompound nbt)
			{
				manager.readFromNBT(nbt);
			}

			@Override
			public boolean hasCapability(Capability<?> capability, EnumFacing facing)
			{
				return capability == REGION_MANAGER;
			}

			@Override
			public <T> T getCapability(Capability<T> capability, EnumFacing facing)
			{
				return GenericUtil.cast(capability == REGION_MANAGER ? manager : null);
			}
		});
	}

	static
	{
		CapabilityManager.INSTANCE.register(RegionManager.class, new Capability.IStorage<RegionManager>()
		{
			@Override
			public NBTBase writeNBT(Capability<RegionManager> capability, RegionManager instance, EnumFacing side)
			{
				return null;
			}

			@Override
			public void readNBT(Capability<RegionManager> capability, RegionManager instance, EnumFacing side, NBTBase nbt)
			{

			}
		}, new Callable<RegionManager>()
		{
			@Override
			public RegionManager call() throws Exception
			{
				return null;
			}
		});
	}
}
