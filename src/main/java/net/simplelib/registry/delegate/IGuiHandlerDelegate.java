package net.simplelib.registry.delegate;

import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.simplelib.registry.abstracts.ASMRegistryDelegate;
import net.simplelib.registry.annotation.type.ASMDelegate;
import net.simplelib.registry.annotation.type.ModGuiHandler;

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
