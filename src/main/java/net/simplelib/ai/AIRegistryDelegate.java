package net.simplelib.ai;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.simplelib.common.CommonLogger;
import net.simplelib.common.registry.abstracts.ASMRegistryDelegate;
import net.simplelib.common.registry.annotation.type.ASMDelegate;
import net.simplelib.common.utils.GenericUtil;

/**
 * @author ci010
 */
@ASMDelegate
public class AIRegistryDelegate extends ASMRegistryDelegate<ModAI>
{
	@Mod.EventHandler
	public void onInit(FMLInitializationEvent event)
	{
		AIHandler provider;
		try
		{
			provider = (AIHandler) this.getAnnotatedClass().newInstance();
			AIRegistry.registerHandler(provider);
			CommonLogger.info("Register AI {} to entity {} by mod [{}]", this.getAnnotatedClass().getSimpleName(),
					GenericUtil.getGenericType(provider), this.getModid());
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
