package net.simplelib.common.registry.delegate;

import com.google.common.collect.ImmutableSet;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.IFuelHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.simplelib.HelperMod;
import net.simplelib.common.CommonLogger;
import net.simplelib.common.registry.abstracts.ASMRegistryDelegate;
import net.simplelib.common.registry.annotation.type.ASMDelegate;
import net.simplelib.common.registry.annotation.type.Handler;

/**
 * @author ci010
 */
@ASMDelegate
public class HandlerDelegate extends ASMRegistryDelegate<Handler>
{
	@Mod.EventHandler
	public void init(FMLInitializationEvent event)
	{
		CommonLogger.info("Start to register the handler [{}] for [{}] mod.", this.getAnnotatedClass(), this.getModid());
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
			HelperMod.LOG.fatal("Cannot create an instance of {}. It will not be registered as a handler.");
		if (IFuelHandler.class.isAssignableFrom(this.getAnnotatedClass()))
			GameRegistry.registerFuelHandler((IFuelHandler) obj);
		ImmutableSet<Handler.Type> set = ImmutableSet.copyOf(this.getAnnotation().value());
		if (set.isEmpty())
		{
			CommonLogger.warn("The handler class [{}] doesn't contain any method needed to be registered!", this.getAnnotatedClass());
			return;
		}
		if (set.contains(Handler.Type.Terrain))
			MinecraftForge.TERRAIN_GEN_BUS.register(obj);
		if (set.contains(Handler.Type.Forge))
		{
			HelperMod.LOG.info("Register this handler to forge event bus.");
			MinecraftForge.EVENT_BUS.register(obj);
		}
		if (set.contains(Handler.Type.FML))
		{
			HelperMod.LOG.info("Register this handler to FML event bus.");
			FMLCommonHandler.instance().bus().register(obj);
		}
		if (set.contains(Handler.Type.OreGen))
			MinecraftForge.ORE_GEN_BUS.register(obj);
	}
}
