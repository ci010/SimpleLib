package net.simplelib.common.registry.delegate;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLConstructionEvent;
import net.simplelib.RegistryHelper;
import api.simplelib.registry.ASMRegistryDelegate;
import api.simplelib.LoadingDelegate;
import api.simplelib.registry.components.ComponentsReference;

/**
 * @author ci010
 */
@LoadingDelegate
public class ReferenceDelegate extends ASMRegistryDelegate<ComponentsReference>
{
	@Mod.EventHandler
	public void construct(FMLConstructionEvent event)
	{
		RegistryHelper.INSTANCE.registerMod(this.getModid(), this.getAnnotatedClass());
	}
}
