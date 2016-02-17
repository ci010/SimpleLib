package api.simplelib.ai;

import net.minecraft.entity.ai.EntityAIBase;

/**
 * @author ci010
 */
public interface AIManager
{
	AIManager removeAI(Class<? extends EntityAIBase> clz);

	AIManager addAI(int piority, EntityAIBase ai);

	AIManager removeAI(int num);
}
