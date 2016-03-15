package net.simplelib.entity;

import api.simplelib.entity.ModPropertyHook;
import api.simplelib.entity.EntityPropertyHook;
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
public class IPropertiesDelegate extends ASMRegistryDelegate<ModPropertyHook>
{
	@Mod.EventHandler
	public void init(FMLInitializationEvent event)
	{
		Class<?> clz = this.getAnnotatedClass();
		if (EntityPropertyHook.class.isAssignableFrom(clz))
		{
			try
			{
				EntityPropertyHook hook = (EntityPropertyHook) clz.newInstance();
				Class<? extends Entity> entityClz = GenericUtil.getInterfaceGenericTypeTo(hook);
				IPropertiesManager.instance().registerStatus(entityClz, hook);
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
