package net.ci010.minecrafthelper.abstracts;

import net.minecraft.entity.ai.EntityAIBase;

/**
 * Created by John on 2015/10/29 0029.
 */
public interface AIMaker<T extends EntityAIBase>
{
	EntityAIBase make(T living);

	int priority();
}
