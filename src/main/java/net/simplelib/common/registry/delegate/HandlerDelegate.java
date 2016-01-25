package net.simplelib.common.registry.delegate;

import com.google.common.collect.ImmutableSet;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.IFuelHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.simplelib.HelperMod;
import net.simplelib.common.CommonLogger;
import net.simplelib.common.registry.abstracts.ASMRegistryDelegate;
import net.simplelib.common.registry.annotation.field.Instance;
import net.simplelib.common.registry.annotation.type.ASMDelegate;
import net.simplelib.common.registry.annotation.type.ModHandler;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * @author ci010
 */
@ASMDelegate
public class HandlerDelegate extends ASMRegistryDelegate<ModHandler>
{
	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event) throws IllegalAccessException
	{
		CommonLogger.info("Start to register the handler [{}] for [{}] mod.", this.getAnnotatedClass(), this.getModid());
		Object obj = null;
		Field field = null;
		boolean setField = false, weak = false;
		for (Field f : this.getAnnotatedClass().getDeclaredFields())
			if (f.isAnnotationPresent(Instance.class))
			{
				Instance anno = f.getAnnotation(Instance.class);
				weak = anno.weak();
				if (Modifier.isStatic(f.getModifiers()))
					field = f;
				else
				{}
			}
//		for (Method method : this.getAnnotatedClass().getDeclaredMethods())
//		{
//			if (method.isAnnotationPresent(Instance.class))
//			{
//				if(method.isAccessible())
//			}
//		}
		if (field != null)
		{
			if (!field.isAccessible())
				field.setAccessible(true);
			obj = field.get(null);
		}
		if (obj == null && !weak)
			try
			{
				obj = this.getAnnotatedClass().newInstance();
				setField = true;
			}
			catch (InstantiationException e)
			{
				e.printStackTrace();
			}
			catch (IllegalAccessException e)
			{
				e.printStackTrace();
			}
		if (field != null && setField)
			try
			{
				field.set(null, obj);
			}
			catch (IllegalAccessException e)
			{
				e.printStackTrace();
			}
		if (obj == null)
		{
			HelperMod.LOG.fatal("Cannot create an instance of {}. It will not be registered as a handler.");
			return;
		}
		if (IFuelHandler.class.isAssignableFrom(this.getAnnotatedClass()))
			GameRegistry.registerFuelHandler((IFuelHandler) obj);
		ImmutableSet<ModHandler.Type> set = ImmutableSet.copyOf(this.getAnnotation().value());
		if (set.isEmpty())
		{
			CommonLogger.warn("The handler class [{}] doesn't contain any method needed to be registered!", this.getAnnotatedClass());
			return;
		}
		if (set.contains(ModHandler.Type.Terrain))
			MinecraftForge.TERRAIN_GEN_BUS.register(obj);
		if (set.contains(ModHandler.Type.Forge))
		{
			HelperMod.LOG.info("Register this handler to forge event bus.");
			MinecraftForge.EVENT_BUS.register(obj);
		}
		if (set.contains(ModHandler.Type.FML))
		{
			HelperMod.LOG.info("Register this handler to FML event bus.");
			FMLCommonHandler.instance().bus().register(obj);
		}
		if (set.contains(ModHandler.Type.OreGen))
			MinecraftForge.ORE_GEN_BUS.register(obj);
	}
}
