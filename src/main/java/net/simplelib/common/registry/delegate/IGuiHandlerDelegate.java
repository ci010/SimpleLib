package net.simplelib.common.registry.delegate;

import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import api.simplelib.registry.ASMRegistryDelegate;
import net.simplelib.common.registry.annotation.type.ASMDelegate;
import api.simplelib.common.ModGuiHandler;

/**
 * @author ci010
 */
@ASMDelegate
public class IGuiHandlerDelegate extends ASMRegistryDelegate<ModGuiHandler>
{
	@Mod.EventHandler
	public void init(FMLInitializationEvent event)
	{
		try
		{
			NetworkRegistry.INSTANCE.registerGuiHandler(Loader.instance().getIndexedModList().get(this.getModid()).getMod(),
					(IGuiHandler) this.getAnnotatedClass().newInstance());
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
