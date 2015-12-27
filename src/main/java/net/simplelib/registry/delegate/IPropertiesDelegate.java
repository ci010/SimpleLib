package net.simplelib.registry.delegate;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.simplelib.abstracts.RegistryDelegate;
import net.simplelib.annotation.type.ASMDelegate;
import net.simplelib.annotation.type.IProperties;
import net.simplelib.status.StatusManager;
import net.simplelib.status.StatusProvider;

/**
 * @author ci010
 */
@ASMDelegate
public class IPropertiesDelegate extends RegistryDelegate<IProperties>
{
	@Mod.EventHandler
	public void init(FMLInitializationEvent event)
	{
		Class<?> clz = this.getAnnotatedClass();
		if (!StatusProvider.class.isAssignableFrom(clz))
			throw new IllegalArgumentException("The class ".concat(clz.getName()).concat("didn't implement " +
					"StatusProvider. It cannot be registered as a status provider!"));
		try
		{
			StatusManager.registerStatus((StatusProvider) clz.newInstance());
		}
		catch (InstantiationException e)
		{
			e.printStackTrace();
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
	}
}
