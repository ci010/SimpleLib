package net.simplelib.world.region;

import api.simplelib.seril.ITagSerializable;
import com.google.common.base.Optional;
import com.google.common.collect.Sets;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.LongHashMap;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.simplelib.world.WorldPropertiesManager;

import java.util.Set;

/**
 * @author ci010
 */
public class RegionManager implements ITagSerializable
{
	private LongHashMap<Region> idToRegion = new LongHashMap<Region>();
	private Set<Region> loadingRegion = Sets.newHashSet();
	private World world;

	public RegionManager(World world)
	{
		this.world = world;
	}

	public World getWorld()
	{
		return world;
	}

	public boolean hasRegion(int x, int z)
	{
		return idToRegion.containsItem(ChunkCoordIntPair.chunkXZ2Int(x, z));
	}

	public Optional<Region> getRegion(int x, int z)
	{
		return Optional.fromNullable(idToRegion.getValueByKey(ChunkCoordIntPair.chunkXZ2Int(x, z)));
	}

	public void newRegion(String id, int x1, int z1, int x2, int z2)
	{
		Region region = new Region(id, world, this);
		region.addRegion(x1, z1, x2, z2);
		long a = ChunkCoordIntPair.chunkXZ2Int(x1, z1);
		long b = ChunkCoordIntPair.chunkXZ2Int(x2, z2);
		if (a == b)
			idToRegion.add(a, region);
		else
		{
			idToRegion.add(a, region);
			idToRegion.add(b, region);
		}
	}

	public void deleteRegion(Region region)
	{
		Set<ChunkCoordIntPair> locations = region.getChunkLocations();
		for (ChunkCoordIntPair location : locations)
			idToRegion.remove(ChunkCoordIntPair.chunkXZ2Int(location.chunkXPos, location.chunkZPos));
	}

	public static void onPlayerEnterRegion(Region region, EntityPlayer player)
	{
		MinecraftForge.EVENT_BUS.post(new Region.EnterEvent(region, player));
	}

	public void onPlayerInRegion(Region region, EntityPlayer player)
	{}

	public static void onPlayerLeaveRegion(Region region, EntityPlayer player)
	{
		MinecraftForge.EVENT_BUS.post(new Region.LeavingEvent(region, player));
	}

	@Override
	public void readFromNBT(NBTTagCompound tag)
	{
	}

	@Override
	public void writeToNBT(NBTTagCompound tag)
	{

	}

	public static Optional<Region> getRegion(World world, BlockPos pos)
	{
		return getRegion(world, pos.getX(), pos.getZ());
	}

	public static Optional<Region> getRegion(World world, int x, int z)
	{
		return WorldPropertiesManager.instance().getWorldCap(world).getCapability(RegionHook.REGION_MANAGER, null)
				.getRegion(x, z);
	}
}
