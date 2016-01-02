package net.simplelib.registry.delegate;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.simplelib.common.CommonLogger;
import net.simplelib.common.utils.GenericUtil;
import net.simplelib.registry.abstracts.ASMRegistryDelegate;
import net.simplelib.registry.annotation.type.ASMDelegate;
import net.simplelib.registry.annotation.type.ModAI;
import net.simplelib.registry.abstracts.AIProvider;
import net.simplelib.registry.AIRegistry;

/**
 * @author ci010
 */
@ASMDelegate
public class AIRegistryDelegate extends ASMRegistryDelegate<ModAI>
{
	@Mod.EventHandler
	public void onInit(FMLInitializationEvent event)
	{
		AIProvider provider;
		try
		{
			provider = (AIProvider) this.getAnnotatedClass().newInstance();
			AIRegistry.registerAI(provider);
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
