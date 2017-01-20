package net.simplelib.registry.delegate;

import api.simplelib.utils.TypeUtils;
import com.google.common.base.Optional;
import com.google.common.collect.ImmutableSet;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import api.simplelib.utils.DebugLogger;
import api.simplelib.registry.ASMRegistryDelegate;
import api.simplelib.Instance;
import api.simplelib.LoadingDelegate;
import api.simplelib.registry.ModHandler;

/**
 * @author ci010
 */
@LoadingDelegate
public class HandlerDelegate extends ASMRegistryDelegate<ModHandler>
{
	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		Optional<?> optional = Instance.Utils.grabAll(this.getAnnotatedClass());
		Object obj;
		if (optional.isPresent())
			obj = optional.get();
		else
			obj = TypeUtils.instantiateQuite(this.getAnnotatedClass());
		if (obj == null)
		{
			DebugLogger.fatal("Cannot create an instance of {}. It will not be registered as a handler.", this.getAnnotatedClass());
			return;
		}

		ImmutableSet<ModHandler.Type> set = ImmutableSet.copyOf(this.getAnnotation().value());
		String info = "Register EventHandler: [";
		for (ModHandler.Type type : set)
			info = info.concat(type.toString()).concat("|");
		info = info.substring(0, info.length() - 1).concat("] <- [{}:{}]");

		DebugLogger.info(info, this.getModid(), this.getAnnotatedClass().getSimpleName());
		if (set.isEmpty())
		{
			DebugLogger.warn("The handler class [{}] doesn't contain any method needed to be registered!",
					this.getAnnotatedClass().getName());
			return;
		}
		if (set.contains(ModHandler.Type.Terrain))
			MinecraftForge.TERRAIN_GEN_BUS.register(obj);
		if (set.contains(ModHandler.Type.Forge) || set.contains(ModHandler.Type.FML))
			MinecraftForge.EVENT_BUS.register(obj);
		if (set.contains(ModHandler.Type.OreGen))
			MinecraftForge.ORE_GEN_BUS.register(obj);
	}
}