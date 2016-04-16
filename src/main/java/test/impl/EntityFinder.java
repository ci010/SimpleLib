package test.impl;

import com.google.common.base.Predicate;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import test.api.component.entity.ComponentEntity;
import test.api.component.entity.StateEntity;
import test.api.component.entity.living.ComponentEntityLiving;
import test.api.component.entity.livingbase.StateEntityLivingBase;
import test.api.component.entity.livingbase.StatePlayer;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

/**
 * @author ci010
 */
public interface EntityFinder
{
	Predicate<StateEntity> ALIVE = new Predicate<StateEntity>()
	{
		@Override
		public boolean apply(@Nullable StateEntity input)
		{
			return input != null && input.isAlive();
		}
	};

	Predicate<StateEntity> LIVING = new Predicate<StateEntity>()
	{
		@Override
		public boolean apply(@Nullable StateEntity input)
		{
			return input instanceof StateEntityLivingBase && ALIVE.apply(input);
		}
	};

	Predicate<StateEntity> PLAYER_COMMON = new Predicate<StateEntity>()
	{
		@Override
		public boolean apply(@Nullable StateEntity input)
		{
			return input instanceof StatePlayer && ALIVE.apply(input);
		}
	};


	StatePlayer getPlayer(String name);

	StatePlayer getPlayer(UUID uuid);

	StatePlayer getClosestPlayer(double x, double y, double z, double distance);

	StatePlayer getClosestPlayerToEntity(StateEntity entityIn, double distance);

	boolean isAnyPlayerWithinRangeAt(double x, double y, double z, double range);

	StateEntity findNearestEntityWithinAABB(ComponentEntity entityType, AxisAlignedBB aabb,
											StateEntity closestTo);

	StateEntityLivingBase findNearestEntityWithinAABB(ComponentEntityLiving entityType, AxisAlignedBB aaabb, StateEntity
			closestTo);

	List<StateEntity> getEntitiesWithinAABB(ComponentEntity entityType, AxisAlignedBB aabb, Predicate<? super
			StateEntity> filter);

	List<StateEntityLivingBase> getLivingsWithinAABB(ComponentEntity entityType, AxisAlignedBB aabb, Predicate<? super
			StateEntityLivingBase> filter);

	List<StateEntity> getEntities(ComponentEntity entityType, Predicate<? super StateEntity> filter);

	List<StateEntity> getEntitiesInAABBexcluding(StateEntity state, AxisAlignedBB boundingBox, Predicate<? super StateEntity> predicate);

	List<StateEntity> getEntitiesInAABBExcluding(StateEntity State, AxisAlignedBB boundingBox);
}
