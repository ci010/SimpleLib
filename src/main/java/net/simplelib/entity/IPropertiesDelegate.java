package net.simplelib.entity;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.simplelib.common.registry.abstracts.ASMRegistryDelegate;
import net.simplelib.common.registry.annotation.type.ASMDelegate;

/**
 * @author ci010
 */
@ASMDelegate
public class IPropertiesDelegate extends ASMRegistryDelegate<IPropertyHook>
{
	@Mod.EventHandler
	public void init(FMLInitializationEvent event)
	{
		Class<?> clz = this.getAnnotatedClass();
		if (!IPropertiesHandler.class.isAssignableFrom(clz))
			throw new IllegalArgumentException("The class ".concat(clz.getName()).concat("didn't implement " +
					"IPropertiesHandler. It cannot be registered as a status provider!"));
		try
		{
			IPropertiesManager.instance().registerStatus(this.getAnnotation().value(), (IPropertiesHandler) clz
					.newInstance());
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
