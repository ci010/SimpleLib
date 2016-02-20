package net.simplelib.entity;

import api.simplelib.entity.PropertyHook;
import api.simplelib.utils.GenericUtil;
import com.google.common.collect.*;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.common.IExtendedEntityProperties;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.simplelib.common.CommonLogger;
import api.simplelib.Instance;
import api.simplelib.common.ModHandler;
import scala.collection.mutable.MultiMap;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * @author ci010
 */
@ModHandler
public class IPropertiesManager
{
	@Instance(weak = true)
	private static IPropertiesManager instance;

	private Multimap<Class<? extends Entity>, PropertyHook> map = HashMultimap.create();
	private StatusCollection collection = new StatusCollection();

	public void registerStatus(Class<? extends Entity> clz, PropertyHook property)
	{
		CommonLogger.info("Register the status handler to all {}.", clz.getSimpleName());
		if (clz == EntityPlayer.class)
		{
			map.put(EntityPlayerSP.class, property);
			map.put(EntityPlayerMP.class, property);
			return;
		}
		map.put(clz, property);
	}

	public static IPropertiesManager instance()
	{
		if (instance == null)
			instance = new IPropertiesManager();
		return instance;
	}

	public IExtendedEntityProperties get(Entity entity, String id)
	{
		return entity.getExtendedProperties(id);
	}

	@SubscribeEvent
	public void onEntityConstructing(EntityEvent.EntityConstructing event)
	{
		for (PropertyHook provider : map.get(event.entity.getClass()))
		{
			collection.set(event.entity);
			provider.handle(event.entity, collection);
		}
	}
}
