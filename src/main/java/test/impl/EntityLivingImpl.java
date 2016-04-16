package test.impl;

import net.minecraft.entity.EntityLiving;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import test.api.component.entity.ComponentEntity;
import test.api.component.entity.livingbase.AbilityLivingBase;
import test.api.component.entity.livingbase.StateEntityLivingBase;

import java.util.UUID;

/**
 * @author ci010
 */
public class EntityLivingImpl extends EntityLiving implements StateEntityLivingBase
{
	public CompiledComponentEntity getInfo()
	{
		return null;
	}

	public EntityLivingImpl(World worldIn)
	{
		super(worldIn);
	}

	public void init()
	{
		CompiledComponentEntity info = this.getInfo();
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing)
	{
		return super.getCapability(capability, facing);
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing)
	{
		return getInfo().hasCapability(capability) || super.hasCapability(capability, facing);
	}

	@Override
	public ComponentEntity getType()
	{
		return getInfo().getComponent();
	}

	@Override
	public AbilityLivingBase getAbility()
	{
		return null;
	}

	@Override
	public BlockPos getPos()
	{
		return this.getPosition();
	}

	@Override
	public test.api.world.World getWorld()
	{
		return null;
	}

	@Override
	public int entityId()
	{
		return this.getEntityId();
	}

	@Override
	public void destroy()
	{
		this.setDead();
	}

	@Override
	public boolean isOnFire()
	{
		return this.isBurning();
	}

	@Override
	public boolean isAlive()
	{
		return this.isEntityAlive();
	}

	@Override
	public UUID entityUniqueID()
	{
		return this.getUniqueID();
	}

	@Override
	public SpecialInventory getArmor()
	{
		return null;
	}

	@Override
	public Health getHealthInfo()
	{
		return new Health()
		{
			@Override
			public void heal(float healAmount)
			{
				EntityLivingImpl.this.heal(healAmount);
			}

			@Override
			public float getHealth()
			{
				return EntityLivingImpl.this.getHealth();
			}

			@Override
			public void setHealth(float health)
			{
				EntityLivingImpl.this.setHealth(health);
			}

			@Override
			public float getMaxHealth()
			{
				return 0;
			}

			@Override
			public void damageEntity(DamageSource damageSrc, float damageAmount)
			{

			}
		};
	}

	@Override
	public boolean isMoving()
	{
		return this.motionX > 0 || this.motionZ > 0;
	}

	@Override
	public boolean isJumping()
	{
		return this.isJumping;
	}
}
