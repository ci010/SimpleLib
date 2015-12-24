package net.simplelib.registry.delegate;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.simplelib.HelperMod;
import net.simplelib.abstracts.RegistryDelegate;
import net.simplelib.annotation.type.ASMDelegate;
import net.simplelib.annotation.type.Handler;

/**
 * @author ci010
 */
@ASMDelegate
public class HandlerDelegate extends RegistryDelegate<Handler>
{
	@Mod.EventHandler
	public void init(FMLInitializationEvent event)
	{
		Object obj = null;
		try
		{
			obj = this.getAnnotatedClass().newInstance();
		}
		catch (InstantiationException e)
		{
			e.printStackTrace();
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		if (obj == null)
			HelperMod.LOG.fatal("");
		if (this.getAnnotation().value() == Handler.Type.Forge)
			MinecraftForge.EVENT_BUS.register(obj);
		if (this.getAnnotation().value() == Handler.Type.FML)
			FMLCommonHandler.instance().bus().register(obj);
		else
		{
			MinecraftForge.EVENT_BUS.register(obj);
			FMLCommonHandler.instance().bus().register(obj);
		}
	}
}
