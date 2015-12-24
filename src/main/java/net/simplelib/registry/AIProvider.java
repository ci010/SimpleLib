package net.simplelib.registry;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;

/**
 * @author ci010
 */
public interface AIProvider<T extends EntityLiving>
{
	EntityAIBase createAI(T entity);

	int priority();
}
