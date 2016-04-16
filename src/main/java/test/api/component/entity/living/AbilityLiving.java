package test.api.component.entity.living;

import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.EntityAIBase;
import test.api.component.entity.StateEntity;
import test.api.component.entity.livingbase.AbilityLivingBase;

/**
 * @author ci010
 */
public interface AbilityLiving extends AbilityLivingBase
{
	boolean canSee(Entity entityIn);

	void setRevengeTarget(StateEntity livingBase);

	StateEntity getLastAttacker();

	int getLastAttackerTime();

	void setLastAttacker(StateEntity entityIn);

	void clearSense();

	void moveTo(double x, double y, double z, double speed);

	double getMovingSpeed();

	void lookAt(Entity entityIn, float deltaYaw, float deltaPitch);

	void look(double x, double y, double z, float deltaYaw, float deltaPitch);

	double getLookPosX();

	double getLookPosY();

	double getLookPosZ();

	void addAI(int priority, EntityAIBase ai);

	void removeAI(EntityAIBase task);

	void removeAI(Class<? extends EntityAIBase> clz);

	void removeAI(int num);
}
