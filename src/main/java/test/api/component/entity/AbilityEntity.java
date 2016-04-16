package test.api.component.entity;

import net.minecraft.util.MovingObjectPosition;

/**
 * @author ci010
 */
public interface AbilityEntity
{
	void setAngle(float yaw, float pitch);

	void setPosition(int x, int y, int z);

	void setFire(int second);

	MovingObjectPosition rayTrace(double blockReachDistance, float partialTicks);
}
