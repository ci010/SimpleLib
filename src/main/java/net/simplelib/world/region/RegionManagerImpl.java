package net.simplelib.world.region;

import api.simplelib.utils.NBTTagListUtils;
import api.simplelib.utils.TypeUtils;
import api.simplelib.world.AttachWorldCapEvent;
import api.simplelib.world.region.Region;
import api.simplelib.world.region.RegionEvent;
import api.simplelib.world.region.RegionManager;
import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.server.management.UserListOpsEntry;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameType;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.event.world.ChunkEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.simplelib.server.PlayerPermission;
import net.simplelib.world.ChunkDataImpl;
import net.simplelib.world.WorldPropertiesManagers;

import java.awt.geom.Rectangle2D;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static api.simplelib.seril.NBTBases.STRING;

/**
 * @author ci010
 */
public class RegionManagerImpl implements RegionManager
{
	private HashMap<String, Region> idToRegion = new HashMap<String, Region>();
	private World world;
	private List<String> pendingToRemove = Lists.newLinkedList();

	public RegionManagerImpl(World world)
	{
		this.world = world;
		if (!world.isRemote)
			MinecraftForge.EVENT_BUS.register(this);
	}

	@Override
	public World getWorld()
	{
		return world;
	}

	@Override
	public Optional<Region> getRegion(int x, int z)
	{
		Chunk chunk = world.getChunkFromChunkCoords(x >> 4, z >> 4);
		List<Integer> removed = null;
		NBTTagList list = regionsTags(chunk);
		String[] ids = NBTTagListUtils.asString(list);
		for(int i = 0; i < ids.length; i++)
		{
			String s = ids[i];
			Region r = idToRegion.get(s);
			if (r == null)
			{
				if (removed == null)
					removed = Lists.newArrayList();
				removed.add(i);
			}
			else if (r.include(x, z))
				return Optional.of(r);
		}
		if (removed != null)
			for(int i = removed.size() - 1; i >= 0; i--)
				list.removeTag(removed.get(i));
		return Optional.absent();
	}

	private NBTTagList regionsTags(Chunk chunk)
	{
		ChunkDataImpl data = ChunkDataImpl.getChunkData(chunk);
		if (!data.getTileData().hasKey("regions"))
		{
			NBTTagList lst = data.getTileData().getTagList("regions", STRING);
			data.getTileData().setTag("regions", lst);
		}
		return data.getTileData().getTagList("regions", STRING);
	}

	@Override
	public Optional<Region> getRegion(BlockPos pos)
	{
		return this.getRegion(pos.getX(), pos.getZ());
	}

	@Override
	public Optional<Region> newRegion(String id, int x1, int z1, int x2, int z2)
	{
		Region region = new RegionImpl(id, this);
		if (region.addRegion(x1, z1, x2, z2))
		{
			this.idToRegion.put(id, region);
			return Optional.of(region);
		}
		return Optional.absent();
	}


	protected boolean onRegionExpand(RegionImpl region, List<Chunk> chunks, Rectangle2D area)
	{
		for(Chunk chunk : chunks)
		{
			NBTTagList regions = regionsTags(chunk);
			String[] strings = NBTTagListUtils.asString(regions);
			for(String string : strings)
			{
				Region r = this.idToRegion.get(string);
				if (r.intersect(area))
				{
					//Log
					return false;
				}
			}
			regions.appendTag(new NBTTagString(region.getId()));
		}
		return true;
	}

	protected void onRegionSubtract(RegionImpl region, List<Chunk> chunks)
	{
		List<Integer> removed;
		for(Chunk chunk : chunks)
		{
			removed = Lists.newArrayList();
			NBTTagList list = regionsTags(chunk);
			String[] ids = NBTTagListUtils.asString(list);
			for(int i = 0; i < ids.length; i++)
				if (ids[i].equals(region.getId()))
					removed.add(i);
			for(int i = removed.size() - 1; i >= 0; i--)
				list.removeTag(removed.get(i));
			removed.clear();
		}
	}

	@Override
	public void deleteRegion(Region region)
	{
		region.removeAll();
		idToRegion.remove(region.getId());
	}

	private static void onPlayerLeaveEnterRegion(Region region, Region newRegion, EntityPlayer player)
	{
		MinecraftForge.EVENT_BUS.post(new RegionEvent.Leave(region, player));
		player.getEntityData().setString("region", newRegion.getId());
		if (player instanceof EntityPlayerMP)
		{
			EntityPlayerMP mp = (EntityPlayerMP) player;
			PlayerPermission perm = player.getCapability(PlayerPermission.CAPABILITY, null);
			UserListOpsEntry userlistopsentry = mp.mcServer.getPlayerList().getOppedPlayers().getEntry(player.getGameProfile());
			if (userlistopsentry == null && !player.isSpectator())
				if (!perm.hasCustomPermission(new ResourceLocation("region:" + region.getId() + ".edit")))
					player.setGameType(GameType.ADVENTURE);
		}
		MinecraftForge.EVENT_BUS.post(new RegionEvent.Enter(newRegion, player));
	}

	private static void onPlayerEnterRegion(Region region, EntityPlayer player)
	{
		player.getEntityData().setString("region", region.getId());
		if (player instanceof EntityPlayerMP)
		{
			EntityPlayerMP mp = (EntityPlayerMP) player;
			PlayerPermission perm = player.getCapability(PlayerPermission.CAPABILITY, null);
			UserListOpsEntry userlistopsentry = mp.mcServer.getPlayerList().getOppedPlayers().getEntry(player.getGameProfile());
			if (userlistopsentry == null && !player.isSpectator())
			{
				if (!perm.hasCustomPermission(new ResourceLocation("region:" + region.getId() + ".edit")))
					player.setGameType(GameType.ADVENTURE);
				NBTTagCompound tag = mp.getEntityData();
				tag.setString("old_mode", mp.interactionManager.getGameType().name());
			}
		}
		MinecraftForge.EVENT_BUS.post(new RegionEvent.Enter(region, player));
	}

	private static void onPlayerLeaveRegion(Region region, EntityPlayer player)
	{
		player.getEntityData().setString("region", "");
		if (player instanceof EntityPlayerMP)
		{
			EntityPlayerMP mp = (EntityPlayerMP) player;
			String old_mode = mp.getEntityData().getString("old_mode");
			if (old_mode.equals(""))
				mp.setGameType(GameType.SURVIVAL);
			else mp.setGameType(GameType.valueOf(old_mode));

		}
		MinecraftForge.EVENT_BUS.post(new RegionEvent.Leave(region, player));
	}

	@SubscribeEvent
	public void onChunkLoad(ChunkEvent.Load event)
	{
		Chunk chunk = event.getChunk();
		String[] ids = NBTTagListUtils.asString(regionsTags(chunk));
		for(String id : ids)
		{
		}
	}

	@SubscribeEvent
	public void onChunkUnload(ChunkEvent.Unload event)
	{
		Chunk chunk = event.getChunk();
		String[] ids = NBTTagListUtils.asString(regionsTags(chunk));
		for(String id : ids)
		{
			Region region = this.idToRegion.get(id);
		}
	}

	@SubscribeEvent
	public void onTick(TickEvent.WorldTickEvent event)
	{
		if (event.phase == TickEvent.Phase.START && event.world == this.world)
		{
			for(EntityPlayer playerEntity : event.world.playerEntities)
			{
				if (playerEntity.lastTickPosX != playerEntity.posX ||
						playerEntity.lastTickPosY != playerEntity.posY ||
						playerEntity.lastTickPosZ != playerEntity.lastTickPosZ)
				{
					NBTTagCompound entityData = playerEntity.getEntityData();

					BlockPos position = playerEntity.getPosition();
					Optional<Region> newRegion = RegionManagerImpl.getInstance(playerEntity.worldObj).getRegion(position);

					if (!entityData.hasKey("region") || entityData.getString("region").equals(""))
					{
						if (newRegion.isPresent())
							onPlayerEnterRegion(newRegion.get(), playerEntity);
					}
					else
					{
						Region region = this.idToRegion.get(entityData.getString("region"));
						if (region == null)
						{
							if (newRegion.isPresent())
								onPlayerEnterRegion(newRegion.get(), playerEntity);
						}
						else if (newRegion.isPresent())
						{
							if (!newRegion.get().equals(region))
								onPlayerLeaveEnterRegion(region, newRegion.get(), playerEntity);
						}
						else
							onPlayerLeaveRegion(region, playerEntity);
					}
				}
			}
		}
	}


	@SubscribeEvent
	public void onWorldCap(AttachWorldCapEvent event)
	{
		event.addCapability(new ResourceLocation("helper", "region"), new RegionManCapProvider(event.getWorld()));
	}

	private static class RegionManCapProvider implements ICapabilitySerializable<NBTTagCompound>
	{
		public RegionManagerImpl manager;

		public RegionManCapProvider(World world)
		{
			this.manager = new RegionManagerImpl(world);
		}

		@Override
		public NBTTagCompound serializeNBT()
		{
			NBTTagCompound tag = new NBTTagCompound();
			for(Map.Entry<String, Region> entry : manager.idToRegion.entrySet())
				tag.setTag(entry.getKey(), entry.getValue().serializeNBT());
			return tag;
		}

		@Override
		public void deserializeNBT(NBTTagCompound nbt)
		{
			for(String s : nbt.getKeySet())
			{
				RegionImpl region = new RegionImpl(s, manager);
				region.deserializeNBT(nbt.getCompoundTag(s));
				manager.idToRegion.put(s, region);
			}
		}

		@Override
		public boolean hasCapability(Capability<?> capability, EnumFacing facing)
		{
			return capability == REGION_MANAGER;
		}

		@Override
		public <T> T getCapability(Capability<T> capability, EnumFacing facing)
		{
			return TypeUtils.cast(capability == REGION_MANAGER ? manager : null);
		}

	}

	public static RegionManager getInstance(World world)
	{
		return WorldPropertiesManagers.instance().getCapabilityProvider(world).getCapability(REGION_MANAGER, null);
	}


}
