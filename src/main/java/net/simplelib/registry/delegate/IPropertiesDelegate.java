package net.simplelib.registry.delegate;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.simplelib.abstracts.RegistryDelegate;
import net.simplelib.annotation.type.ASMDelegate;
import net.simplelib.annotation.type.IProperties;

/**
 * @author ci010
 */
@ASMDelegate
public class IPropertiesDelegate extends RegistryDelegate<IProperties>
{
	@Mod.EventHandler
	public void init(FMLInitializationEvent event)
	{

	}
}
