package api.simplelib.ai;

import net.minecraft.entity.EntityLiving;

/**
 * @author ci010
 */
public interface AIHandler<E extends EntityLiving>
{
	void handleAI(E entity, AIManager manager);
}
