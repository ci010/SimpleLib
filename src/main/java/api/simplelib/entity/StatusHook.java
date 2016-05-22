package api.simplelib.entity;

import api.simplelib.registry.ModProxy;
import api.simplelib.utils.Nullable;
import net.minecraft.entity.Entity;
import net.simplelib.entity.IPropertiesManager;

/**
 * @author ci010
 */
public interface StatusHook
{
	@ModProxy.Inject
	StatusHook INSTANCE = null;

	@Nullable
	IStatus getStatus(Entity entity, String id);
}
