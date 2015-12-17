package net.ci010.minecrafthelper.data.delegate;

import net.ci010.minecrafthelper.abstracts.RegistryDelegate;
import net.ci010.minecrafthelper.annotation.type.ASMDelegate;
import net.ci010.minecrafthelper.annotation.type.IProperties;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

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
