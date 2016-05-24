package net.simplelib.entity;

import api.simplelib.registry.ModEntityHandler;
import api.simplelib.entity.EntityHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import api.simplelib.registry.ASMRegistryDelegate;
import api.simplelib.LoadingDelegate;

/**
 * @author ci010
 */
@LoadingDelegate
public class IPropertiesDelegate extends ASMRegistryDelegate<ModEntityHandler>
{
	@Mod.EventHandler
	public void init(FMLInitializationEvent event)
	{
		Class<?> clz = this.getAnnotatedClass();
		if (EntityHandler.class.isAssignableFrom(clz))
		{
			try
			{
				EntityHandler hook = (EntityHandler) clz.newInstance();
				IPropertiesManager.instance().registerStatus(hook);

//				CommonLogger.info("Register AI {} to entity {} by mod [{}]", this.getAnnotatedClass().getSimpleName(),
//						TypeUtils.getGenericType(provider), this.getModid());
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
}
