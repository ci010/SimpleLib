package net.ci010.minecrafthelper.data.delegate;

import net.ci010.minecrafthelper.HelperMod;
import net.ci010.minecrafthelper.abstracts.RegistryDelegate;
import net.ci010.minecrafthelper.annotation.type.ASMDelegate;
import net.ci010.minecrafthelper.annotation.type.ModGuiHandler;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;

/**
 * @author ci010
 */
@ASMDelegate
public class IGuiHandlerDelegate extends RegistryDelegate<ModGuiHandler>
{
	@Mod.EventHandler
	public void init(FMLInitializationEvent event)
	{
		try
		{
			NetworkRegistry.INSTANCE.registerGuiHandler(Loader.instance().getIndexedModList().get(this.getModid()).getMod(), (IGuiHandler) this.getAnnotatedClass().newInstance());
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
