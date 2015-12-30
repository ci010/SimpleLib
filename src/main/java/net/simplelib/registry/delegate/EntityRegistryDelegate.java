package net.simplelib.registry.delegate;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.item.ItemMonsterPlacer;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.simplelib.CommonLogger;
import net.simplelib.HelperMod;
import net.simplelib.RegistryHelper;
import net.simplelib.abstracts.RegistryDelegate;
import net.simplelib.annotation.type.ASMDelegate;
import net.simplelib.annotation.type.ModEntity;
import net.simplelib.util.FMLModUtil;
import net.simplelib.util.GenericUtil;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

/**
 * @author ci010
 */
@ASMDelegate
public class EntityRegistryDelegate extends RegistryDelegate<ModEntity>
{
	static Map<String, Integer> modidCache = Maps.newHashMap();

	static int nextId(String modid)
	{
		int next = 0;
		if (!modidCache.containsKey(modid))
			modidCache.put(modid, 0);
		else
		{
			next = modidCache.get(modid) + 1;
			modidCache.put(modid, next);
		}
		return next + 128;
	}

	@Mod.EventHandler
	public void init(FMLInitializationEvent event)
	{
		String modid = this.getModid();
		ModEntity anno = this.getAnnotation();
		Class<? extends net.minecraft.entity.Entity> clz = GenericUtil.cast(this.getAnnotatedClass());
		String name = anno.name();
		Object mod = FMLModUtil.getModContainer(modid).getMod();
		if (name.equals(""))
			name = clz.getSimpleName();
		int id = anno.id();
		if (id == -1)
			id = nextId(modid);
		ModEntity.HasSpawner hasSpawner = this.getAnnotatedClass().getAnnotation(ModEntity.HasSpawner.class);
		if (hasSpawner != null)
		{
			CommonLogger.info("Register entity {} for mod {} with id {} and its egg.", name, modid, id);
			EntityRegistry.registerModEntity(clz, name, id, mod, anno.trackingRange(), anno
					.updateFrequency(), anno.sendsVelocityUpdates(), hasSpawner.primaryColor(), hasSpawner.secondaryColor());
		}
		else
		{
			CommonLogger.info("Register entity {} for mod {} with id {}", name, modid, id);
			EntityRegistry.registerModEntity(clz, name, id, mod, anno.trackingRange(), anno
					.updateFrequency(), anno.sendsVelocityUpdates());
		}
		if (HelperMod.proxy.isClient())
		{
			if (clz.isAnnotationPresent(ModEntity.Render.class))
			{
				Class<? extends Render> render = clz.getAnnotation(ModEntity.Render.class).value();
				Render renderObj = null;
				try
				{
					renderObj = render.newInstance();
				}
				catch (InstantiationException e)
				{
					e.printStackTrace();
				}
				catch (IllegalAccessException e)
				{
					try
					{
						Constructor constructor = render.getConstructor();
						constructor.setAccessible(true);
						renderObj = (Render) constructor.newInstance();
					}
					catch (NoSuchMethodException e1)
					{
						CommonLogger.fatal("Cannot create the instance of {}'s renderer. There should be a " +
								"constructor without any parameter for the renderer class. The entity {} won't be register a " +
								"renderer.", name, name);
						return;
					}
					catch (IllegalAccessException e1)
					{
						e1.printStackTrace();
					}
					catch (InstantiationException e1)
					{
						e1.printStackTrace();
					}
					catch (InvocationTargetException e1)
					{
						e1.printStackTrace();
					}
				}
				RenderingRegistry.registerEntityRenderingHandler(clz, renderObj);
				CommonLogger.info("Register renderer to entity {}", name);
			}
		}
	}
}
