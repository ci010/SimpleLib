package api.simplelib.utils;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityDispatcher;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.simplelib.world.WorldPropertiesManager;

/**
 * @author ci010
 */
public class CapabilityUtils
{
	public static <T> T getCapability(World world, Capability<T> capability)
	{
		return WorldPropertiesManager.instance().getWorldCap(world).getCapability(capability, null);
	}

	public static <T> T getCapability(Entity entity, Capability<T> capability)
	{
		return entity.getCapability(capability, null);
	}

	public static CapabilityDispatcher gatherCapabilities(AttachCapabilitiesEvent event)
	{
		MinecraftForge.EVENT_BUS.post(event);
		return event.getCapabilities().size() > 0 ? new CapabilityDispatcher(event.getCapabilities()) : null;
	}

}
