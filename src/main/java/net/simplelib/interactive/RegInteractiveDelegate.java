package net.simplelib.interactive;

import test.interactive.Interactive;
import test.interactive.ModInteractive;
import api.simplelib.registry.ASMRegistryDelegate;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import api.simplelib.LoadingDelegate;

/**
 * @author ci010
 */
@LoadingDelegate
public class RegInteractiveDelegate extends ASMRegistryDelegate<ModInteractive>
{
	@Mod.EventHandler
	public void init(FMLInitializationEvent event)
	{
		try
		{
			Interactive interactive = (Interactive) this.getAnnotatedClass().newInstance();
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
