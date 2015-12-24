package net.simplelib.registry.delegate;

import com.google.common.collect.Lists;
import net.minecraft.client.renderer.entity.Render;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.simplelib.HelperMod;
import net.simplelib.abstracts.RegistryDelegate;
import net.simplelib.annotation.type.ASMDelegate;
import net.simplelib.annotation.type.ModEntity;
import net.simplelib.util.FMLModUtil;
import net.simplelib.util.GenericUtil;

import java.util.List;

/**
 * @author ci010
 */
@ASMDelegate
public class EntityRegistryDelegate extends RegistryDelegate<ModEntity>
{
	static List<String> modidCache = Lists.newArrayList();

	static int nextId(String modid)
	{
		int next;
		if (!modidCache.contains(modid))
		{
			modidCache.add(modid);
			next = modidCache.size() - 1;
		}
		else
			next = modidCache.indexOf(modid);
		return next;
	}

	@Mod.EventHandler
	public void init(FMLInitializationEvent event)
	{
		String modid = this.getModid();
		ModEntity anno = this.getAnnotation();
		Class<? extends net.minecraft.entity.Entity> clz = GenericUtil.cast(this.getAnnotatedClass());
		String name = anno.name();
		if (name.equals(""))
			name = clz.getName();
		int id = anno.id();
		if (id == -1)
			id = nextId(modid);
		EntityRegistry.registerModEntity(clz, name, id, FMLModUtil.getModContainer(modid).getMod(), anno.trackingRange(), anno
				.updateFrequency(), anno.sendsVelocityUpdates());
		if (HelperMod.proxy.isClient())
		{
			if (clz.isAnnotationPresent(ModEntity.Render.class))
			{
				Class<? extends Render> render = clz.getAnnotation(ModEntity.Render.class).value();
				try
				{
					RenderingRegistry.registerEntityRenderingHandler(clz, render.newInstance());
				}
				catch (InstantiationException e)
				{

					e.printStackTrace();
				}
				catch (IllegalAccessException e)
				{
					// TODO: 2015/12/3 LOG
					e.printStackTrace();
				}
			}
		}
	}
}
