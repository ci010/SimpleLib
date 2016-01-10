package net.simplelib.common.registry.delegate;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.simplelib.common.registry.abstracts.ASMRegistryDelegate;
import net.simplelib.common.registry.annotation.type.ASMDelegate;
import net.simplelib.common.registry.annotation.type.IPropertyHook;
import net.simplelib.status.StatusManager;
import net.simplelib.status.StatusProvider;

import java.lang.reflect.Type;

/**
 * @author ci010
 */
//@ASMDelegate
public class IPropertiesDelegate extends ASMRegistryDelegate<IPropertyHook>
{
	@Mod.EventHandler
	public void init(FMLInitializationEvent event)
	{
		Class<?> clz = this.getAnnotatedClass();
		if (!StatusProvider.class.isAssignableFrom(clz))
			throw new IllegalArgumentException("The class ".concat(clz.getName()).concat("didn't implement " +
					"StatusProvider. It cannot be registered as a status provider!"));
		System.out.println(clz.getName());
		for (Type type : clz.getGenericInterfaces())
		{
			System.out.println(type);

		}
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
