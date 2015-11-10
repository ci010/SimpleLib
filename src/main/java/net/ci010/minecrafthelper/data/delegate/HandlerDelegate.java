package net.ci010.minecrafthelper.data.delegate;

import net.ci010.minecrafthelper.abstracts.RegistryDelegate;
import net.ci010.minecrafthelper.annotation.type.ASMDelegate;
import net.ci010.minecrafthelper.annotation.type.Handler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

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

		System.out.println("Event handler run");
		MinecraftForge.EVENT_BUS.register(obj);
		FMLCommonHandler.instance().bus().register(obj);
	}
}
