package net.simplelib.interactive;

import api.simplelib.interactive.Interactive;
import api.simplelib.interactive.ModInteractive;
import api.simplelib.registry.ASMRegistryDelegate;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.simplelib.common.registry.annotation.type.ASMDelegate;

/**
 * @author ci010
 */
@ASMDelegate
public class RegInteractiveDelegate extends ASMRegistryDelegate<ModInteractive>
{
	@Mod.EventHandler
	public void init(FMLInitializationEvent event)
	{
		try
		{
			Interactive interactive = (Interactive) this.getAnnotatedClass().newInstance();
			InteractiveMetadata.getBaseHandler(interactive.getBase()).handle(interactive);
			InteractiveMetadata.register(interactive);
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
