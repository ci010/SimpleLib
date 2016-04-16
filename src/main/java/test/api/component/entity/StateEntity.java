package test.api.component.entity;

import net.minecraft.util.BlockPos;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import test.api.component.State;
import test.api.world.World;

import java.util.UUID;

/**
 * @author ci010
 */
public interface StateEntity extends State, ICapabilityProvider
{
	@Override
	ComponentEntity getType();

	AbilityEntity getAbility();

	BlockPos getPos();

	World getWorld();

	int entityId();

	void destroy();

	void setCustomNameTag(String name);

	String getCustomNameTag();

	boolean hasCustomName();

	boolean isInWater();

	boolean isOnFire();

	boolean isAlive();

	boolean isInLava();

	boolean isImmuneToFire();

	boolean isInvisible();

	UUID entityUniqueID();
}
