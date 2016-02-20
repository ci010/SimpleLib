package net.simplelib.common.registry.delegate;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLConstructionEvent;
import net.simplelib.RegistryHelper;
import api.simplelib.registry.ASMRegistryDelegate;
import net.simplelib.common.registry.annotation.type.ASMDelegate;
import api.simplelib.component.ComponentsReference;

/**
 * @author ci010
 */
@ASMDelegate
public class ReferenceDelegate extends ASMRegistryDelegate<ComponentsReference>
{
	@Mod.EventHandler
	public void construct(FMLConstructionEvent event)
	{
		RegistryHelper.INSTANCE.registerMod(this.getModid(), this.getAnnotatedClass());
	}
}
