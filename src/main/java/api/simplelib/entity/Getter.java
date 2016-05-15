package api.simplelib.entity;

import api.simplelib.utils.Nullable;
import net.minecraft.entity.Entity;
import net.simplelib.entity.IPropertiesManager;

/**
 * @author ci010
 */
public class Getter
{
	@Nullable
	public static IStatus getStatus(Entity entity, String id)
	{
		if (IPropertiesManager.enable())
			return IPropertiesManager.instance().getStatus(entity, id);
		return null;
	}
}
