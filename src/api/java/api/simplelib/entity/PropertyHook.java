package api.simplelib.entity;

import net.minecraft.entity.Entity;
import net.minecraftforge.common.IExtendedEntityProperties;

/**
 * The hook handle the entity's custom properties.
 * <p>You can register {@link IExtendedEntityProperties} and {@link IStatus} to entity here.<p/>
 * <p>Use {@link ModPropertyHook} to register this.</p>
 *
 * @author ci010
 * @see ModPropertyHook
 * @see IExtendedEntityProperties
 * @see IStatus
 */
public interface PropertyHook<E extends Entity>
{
	/**
	 * Handle a new entity.
	 *
	 * @param entity  The entity you want to handle.
	 * @param handler The handler which provide you ability to add custom properties to the entity..
	 */
	void handle(E entity, Handler handler);

	interface Handler
	{
		Handler add(String id, IExtendedEntityProperties properties);

		Handler add(String id, IStatus status);
	}
}
