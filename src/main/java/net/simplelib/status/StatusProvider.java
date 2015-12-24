package net.simplelib.status;

import net.minecraft.entity.Entity;

/**
 * @author ci010
 */
public interface StatusProvider<T extends Entity>
{
	Status createStatus(T entity);

	Status getStatus(T entity);

	String getId();
}
