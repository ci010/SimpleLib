package api.simplelib.remote.capabilities;

import api.simplelib.gui.ComponentProvider;
import api.simplelib.inventory.Inventory;
import api.simplelib.remote.Syncable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

/**
 * @author ci010
 */
public class CapabilitiesCommon
{
	@CapabilityInject(ComponentProvider.class)
	public static final Capability<ComponentProvider> COMPONENTS = null;

	@CapabilityInject(Inventory.class)
	public static final Capability<Inventory> INVENTORY = null;

	@CapabilityInject(Syncable.class)
	public static final Capability<Syncable> SYNC = null;
}
