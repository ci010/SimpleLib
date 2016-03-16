package net.simplelib.entity;

import api.simplelib.entity.ModEntityHandler;
import api.simplelib.entity.EntityHandler;
import api.simplelib.utils.GenericUtil;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import api.simplelib.registry.ASMRegistryDelegate;
import net.simplelib.common.registry.annotation.type.ASMDelegate;

/**
 * @author ci010
 */
@ASMDelegate
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
				Class<? extends Entity> entityClz = GenericUtil.getInterfaceGenericTypeTo(hook);
				IPropertiesManager.instance().registerStatus(entityClz, hook);

//				CommonLogger.info("Register AI {} to entity {} by mod [{}]", this.getAnnotatedClass().getSimpleName(),
//						GenericUtil.getGenericType(provider), this.getModid());
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
