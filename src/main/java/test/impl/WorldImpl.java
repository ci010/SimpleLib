package test.impl;

import com.google.common.base.Predicate;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.IntHashMap;
import net.minecraft.world.biome.BiomeGenBase;
import test.api.component.block.StateBlock;
import test.api.component.entity.ComponentEntity;
import test.api.component.entity.StateEntity;
import test.api.component.entity.living.ComponentEntityLiving;
import test.api.component.entity.livingbase.StateEntityLivingBase;
import test.api.component.entity.livingbase.StatePlayer;
import test.api.component.item.StateItem;
import test.api.world.World;

import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author ci010
 */
public class WorldImpl implements World, EntityFinder
{
	private net.minecraft.world.World delegate;
	private IntHashMap<StateEntity> entities = new IntHashMap<StateEntity>();
	private Queue<StateStackImpl> recycleStateItems = new ConcurrentLinkedQueue<StateStackImpl>();
	private Queue<StateBlockImpl> recycleStateBlocks = new ConcurrentLinkedQueue<StateBlockImpl>();

	public WorldImpl(net.minecraft.world.World delegate)
	{
		this.delegate = delegate;
	}

	public static WorldImpl of(net.minecraft.world.World world)
	{
		return new WorldImpl(world);
	}

	public StateEntity of(Entity entity)
	{
		if (entities.containsItem(entity.getEntityId()))
			return entities.lookup(entity.getEntityId());
		StateEntityImpl stateEntity = new StateEntityImpl(entity);
		entities.addKey(entity.getEntityId(), stateEntity);
		return stateEntity;
	}

	public StateBlock of(BlockPos pos, IBlockState state)
	{
		Block block = state.getBlock();
		StateBlockImpl poll = recycleStateBlocks.poll();
		if (poll == null)
			poll = new StateBlockImpl(this);
		if (block.hasTileEntity(state))
			return poll.with(delegate.getTileEntity(pos)).with(state);
		return poll.with(state);
	}

	public StateItem of(ItemStack stack)
	{
		StateStackImpl poll = recycleStateItems.poll();
		if (poll != null)
			return poll.with(stack);
		else
			return new StateStackImpl(this).with(stack);
	}

	void recycle(StateStackImpl stateItem) { recycleStateItems.add(stateItem);}

	void recycle(StateBlockImpl stateBlock) { recycleStateBlocks.add(stateBlock);}

	@Override
	public StateEntity getEntity(int id)
	{
		return entities.lookup(id);
	}

	@Override
	public EntityFinder getEntityFinder()
	{
		return null;
	}

	@Override
	public void playSoundAtEntity(StatePlayer playerIn, String sound, float v, float v1)
	{

	}

	@Override
	public Random getRandom()
	{
		return delegate.rand;
	}

	@Override
	public void dropItem(StateItem item, BlockPos pos)
	{

	}

	@Override
	public StateBlock getBlockState(BlockPos pos)
	{
		return of(pos, delegate.getBlockState(pos));
	}

	@Override
	public BiomeGenBase getBiome(BlockPos pos)
	{
		return null;
	}

	@Override
	public boolean isRemote()
	{
		return false;
	}

	@Override
	public int getHeight(int x, int z)
	{
		return 0;
	}

	@Override
	public boolean isDaytime()
	{
		return false;
	}

	@Override
	public int getSeaLevel()
	{
		return 0;
	}

	@Override
	public long getWorldTime()
	{
		return 0;
	}

	@Override
	public long getSeed()
	{
		return 0;
	}

	@Override
	public void setWorldTime(long time)
	{

	}

	@Override
	public long getTotalWorldTime()
	{
		return 0;
	}

	@Override
	public BlockPos getSpawnPoint()
	{
		return delegate.getSpawnPoint();
	}

	@Override
	public void setSpawnPoint(BlockPos pos)
	{
		delegate.setSpawnPoint(pos);
	}

	@Override
	public StatePlayer getPlayer(String name)
	{
		return (StatePlayer) of(delegate.getPlayerEntityByName(name));
	}

	@Override
	public StatePlayer getPlayer(UUID uuid)
	{
		return (StatePlayer) of(delegate.getPlayerEntityByUUID(uuid));
	}

	@Override
	public StatePlayer getClosestPlayer(double x, double y, double z, double distance)
	{
		return (StatePlayer) of(delegate.getClosestPlayer(x, y, z, distance));
	}

	@Override
	public StatePlayer getClosestPlayerToEntity(StateEntity entityIn, double distance)
	{
		return (StatePlayer) of(delegate.getClosestPlayerToEntity(((StateEntityImpl) entityIn).delegate, distance));
	}

	@Override
	public boolean isAnyPlayerWithinRangeAt(double x, double y, double z, double range)
	{
		return delegate.isAnyPlayerWithinRangeAt(x, y, z, range);
	}

	@Override
	public StateEntity findNearestEntityWithinAABB(ComponentEntity entityType, AxisAlignedBB aabb, StateEntity closestTo)
	{
		return null;
	}

	@Override
	public StateEntityLivingBase findNearestEntityWithinAABB(ComponentEntityLiving entityType, AxisAlignedBB aaabb, StateEntity closestTo)
	{
		return null;
	}

	@Override
	public List<StateEntity> getEntitiesWithinAABB(ComponentEntity entityType, AxisAlignedBB aabb, Predicate<? super StateEntity> filter)
	{
		return null;
	}

	@Override
	public List<StateEntityLivingBase> getLivingsWithinAABB(ComponentEntity entityType, AxisAlignedBB aabb, Predicate<? super StateEntityLivingBase> filter)
	{
		return null;
	}

	@Override
	public List<StateEntity> getEntities(ComponentEntity entityType, Predicate<? super StateEntity> filter)
	{
		return null;
	}

	@Override
	public List<StateEntity> getEntitiesInAABBexcluding(StateEntity state, AxisAlignedBB boundingBox, Predicate<? super StateEntity> predicate)
	{
		return null;
	}

	@Override
	public List<StateEntity> getEntitiesInAABBExcluding(StateEntity State, AxisAlignedBB boundingBox)
	{
		return null;
	}
}
