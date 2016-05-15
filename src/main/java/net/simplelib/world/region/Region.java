package net.simplelib.world.region;

import api.simplelib.utils.CapabilityUtils;
import api.simplelib.utils.ChunkUtils;
import api.simplelib.utils.NBTTagBuilder;
import com.google.common.collect.ImmutableSet;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityDispatcher;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.simplelib.common.RangeBase;

import java.awt.*;
import java.awt.geom.Area;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author ci010
 */
public class Region implements ICapabilitySerializable<NBTTagCompound>
{
	private static Comparator<ChunkCoordIntPair> comparator = new Comparator<ChunkCoordIntPair>()
	{
		@Override
		public int compare(ChunkCoordIntPair o1, ChunkCoordIntPair o2)
		{
			return o1.chunkXPos + o1.chunkZPos - o2.chunkXPos - o2.chunkZPos;
		}
	};
	private Area area;
	private RegionManager manager;
	private World world;
	private final String id;
	private Set<ChunkCoordIntPair> allChunks = new TreeSet<ChunkCoordIntPair>(comparator), loadingChunk = new
			TreeSet<ChunkCoordIntPair>(comparator);

	private String name;

	private CapabilityDispatcher dispatcher;

	public Region(String id, World world, RegionManager manager)
	{
		this.id = id;
		this.manager = manager;
		this.world = world;
		this.area = new Area();
		this.dispatcher = CapabilityUtils.gatherCapabilities(new AttachRegionEvent(this));
	}

	public World getWorld()
	{
		return world;
	}

	public void addRegion(int x1, int z1, int x2, int z2)
	{
		allChunks.add(ChunkUtils.getAsChunkCoord(x1, z1));
		allChunks.add(ChunkUtils.getAsChunkCoord(x2, z2));
		area.add(new Area(newRectangle(x1, z1, x2, z2)));
	}

	private Rectangle newRectangle(int x1, int z1, int x2, int z2)
	{
		int x = Math.min(x1, x2), z = Math.min(z1, z2), xSize = Math.abs(x1 - x2), zSize = Math.abs(z1 - z2);
		return new Rectangle(x, z, xSize, zSize);
	}

	public boolean enable()
	{
		return false;
	}

	public void removeRegion(int x1, int z1, int x2, int z2)
	{
		allChunks.remove(ChunkUtils.getAsChunkCoord(x1, z1));
		allChunks.remove(ChunkUtils.getAsChunkCoord(x2, z2));
		area.subtract(new Area(newRectangle(x1, z1, x2, z2)));
		if (area.isEmpty())
			manager.deleteRegion(this);
	}

	public Set<ChunkCoordIntPair> getChunkLocations()
	{
		return ImmutableSet.copyOf(this.allChunks);
	}

	public boolean include(int x, int z)
	{
		return area.contains(x, z);
	}

	public String getId()
	{
		return id;
	}

	public String getName()
	{
		return name;
	}

	public Region setName(String nameKey)
	{
		this.name = nameKey;
		return this;
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing)
	{
		return dispatcher != null && dispatcher.hasCapability(capability, facing);
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing)
	{
		return dispatcher == null ? null : dispatcher.getCapability(capability, facing);
	}

	@Override
	public NBTTagCompound serializeNBT()
	{
		return NBTTagBuilder.newBuilder()
				.addString("name", name)
//				.addInt("posX", this.pos.chunkXPos)
//				.addInt("posZ", this.pos.chunkZPos)
				.addTag("capabilities", dispatcher.serializeNBT())
				.build();
	}

	@Override
	public void deserializeNBT(NBTTagCompound nbt)
	{
		name = nbt.getString("name");
//		pos = new ChunkCoordIntPair(nbt.getInteger("posX"), nbt.getInteger("posZ"));
		dispatcher.deserializeNBT(nbt.getCompoundTag("capabilities"));
	}

	private static class RegionEvent extends Event
	{
		public final Region region;
		public final EntityPlayer player;

		public RegionEvent(Region region, EntityPlayer player)
		{
			this.region = region;
			this.player = player;
		}
	}

	public static class AttachRegionEvent extends AttachCapabilitiesEvent
	{
		private Region region;

		public AttachRegionEvent(Region obj)
		{
			super(obj);
			this.region = obj;
		}

		public Region getRegion()
		{
			return region;
		}
	}

	public static class EnterEvent extends RegionEvent
	{
		public EnterEvent(Region region, EntityPlayer player)
		{
			super(region, player);
		}
	}

	public static class LeavingEvent extends RegionEvent
	{
		public LeavingEvent(Region region, EntityPlayer player)
		{
			super(region, player);
		}
	}

	interface Height
	{
		Height Default = new Height()
		{
			RangeBase r = new RangeBase(40, 80);

			@Override
			public RangeBase get(int x, int z)
			{
				return r;
			}
		};

		RangeBase get(int x, int z);
	}
}
