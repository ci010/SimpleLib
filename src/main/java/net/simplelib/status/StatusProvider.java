package net.simplelib.status;

import net.minecraft.entity.Entity;

/**
 * @author ci010
 */
public interface StatusProvider<T extends Entity, S extends Status>
{
	S createStatus(T entity);

	String getId();
}
