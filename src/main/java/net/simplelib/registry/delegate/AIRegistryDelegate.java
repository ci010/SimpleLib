package net.simplelib.registry.delegate;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.simplelib.abstracts.RegistryDelegate;
import net.simplelib.annotation.type.ASMDelegate;
import net.simplelib.annotation.type.ModAI;
import net.simplelib.registry.AIProvider;
import net.simplelib.registry.AIRegistry;

/**
 * @author ci010
 */
@ASMDelegate
public class AIRegistryDelegate extends RegistryDelegate<ModAI>
{
	@Mod.EventHandler
	public void onInit(FMLInitializationEvent event)
	{
		try
		{
			AIRegistry.registerAI((AIProvider) this.getAnnotatedClass().newInstance());
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
