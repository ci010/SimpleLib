package api.simplelib.utils;

import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;


/**
 * @author Mickey
 * @author ci010
 */
public class AABBUtils
{
	public static AxisAlignedBB getAABBFromEntity(Entity entity, double r)
	{
		return new AxisAlignedBB(entity.posX - r, entity.posY - r, entity.posZ - r, entity.posX + r, entity.posY + r,
				entity.posZ + r);
	}
}
