package net.ci010.minecrafthelper.status;

import net.minecraft.entity.Entity;

/**
 * @author ci010
 */
public interface StatusProvider<E extends Entity, T extends Status<E>>
{
	T createStatus(E entity);

	T getStatus(E entity);
}
