package api.simplelib.capabilities;

import api.simplelib.LoadingDelegate;
import api.simplelib.registry.ASMRegistryDelegate;
import api.simplelib.utils.TypeUtils;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * @author ci010
 */
@LoadingDelegate
public class CapabilityDelegate extends ASMRegistryDelegate<ModCapability>
{
	@SubscribeEvent
	public void init(FMLPreInitializationEvent event)
	{
		if (ICapability.class.isAssignableFrom(this.getAnnotatedClass()))
		{
			ICapability capability = TypeUtils.cast(TypeUtils.instantiateQuite(this.getAnnotatedClass()));
			CapabilityManager.INSTANCE.register(capability.type(), capability.storage(), capability.factory());
		}
	}
}
