package test.impl;

import net.minecraft.entity.Entity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MovingObjectPosition;
import net.minecraftforge.common.capabilities.Capability;
import test.api.component.entity.AbilityEntity;
import test.api.component.entity.ComponentEntity;
import test.api.component.entity.StateEntity;
import test.api.world.World;

import java.util.UUID;

/**
 * @author ci010
 */
public class StateEntityImpl implements StateEntity, AbilityEntity
{
	Entity delegate;
	World world;
	private BlockPos pos;//TODO maybe use world tick event
	ComponentEntity type;

	StateEntityImpl(World world, Entity entity)
	{
		this.world = world;
		this.delegate = entity;
		type = GameComponents.get(entity);
	}

	@Override
	public void setAngle(float yaw, float pitch)
	{
		delegate.setAngles(yaw, pitch);
	}

	@Override
	public void setPosition(int x, int y, int z)
	{
		delegate.setPosition(x, y, z);
	}

	@Override
	public void setFire(int second)
	{
		delegate.setFire(second);
	}

	@Override
	public MovingObjectPosition rayTrace(double blockReachDistance, float partialTicks)
	{
		return delegate.rayTrace(blockReachDistance, partialTicks);
	}

	public StateEntityImpl(Entity delegate)
	{
		this.delegate = delegate;
	}

	@Override
	public ComponentEntity getType()
	{
		return type;
	}

	@Override
	public AbilityEntity getAbility()
	{
		return this;
	}

	@Override
	public BlockPos getPos()
	{
		return pos;
	}

	@Override
	public World getWorld()
	{
		return world;
	}

	@Override
	public int entityId()
	{
		return delegate.getEntityId();
	}

	@Override
	public void destroy()
	{
		delegate.setDead();
	}

	@Override
	public void setCustomNameTag(String name)
	{
		delegate.setCustomNameTag(name);
	}

	@Override
	public String getCustomNameTag()
	{
		return delegate.getCustomNameTag();
	}

	@Override
	public boolean hasCustomName()
	{
		return delegate.hasCustomName();
	}

	@Override
	public boolean isInWater()
	{
		return delegate.isInWater();
	}

	@Override
	public boolean isInLava() {return delegate.isInLava();}

	@Override
	public boolean isOnFire()
	{
		return delegate.isBurning();
	}

	@Override
	public boolean isAlive()
	{
		return delegate.isEntityAlive();
	}

	@Override
	public boolean isImmuneToFire()
	{
		return delegate.isImmuneToFire();
	}

	@Override
	public boolean isInvisible()
	{
		return delegate.isInvisible();
	}

	@Override
	public UUID entityUniqueID()
	{
		return delegate.getUniqueID();
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing)
	{
		return delegate.hasCapability(capability, facing);
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing)
	{
		return delegate.getCapability(capability, facing);
	}
}
