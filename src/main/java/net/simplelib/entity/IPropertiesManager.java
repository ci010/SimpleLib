package net.simplelib.entity;

import api.simplelib.common.Nullable;
import api.simplelib.entity.IStatus;
import api.simplelib.entity.EntityHandler;
import com.google.common.collect.*;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.common.IExtendedEntityProperties;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.simplelib.common.CommonLogger;
import api.simplelib.common.Instance;
import api.simplelib.common.ModHandler;

/**
 * @author ci010
 */
@ModHandler
public class IPropertiesManager
{
	@Instance(weak = true)
	private static IPropertiesManager instance;

	private Multimap<Class<? extends Entity>, EntityHandler> map = HashMultimap.create();
	private StatusCollection collection = new StatusCollection();

	public void registerStatus(Class<? extends Entity> clz, EntityHandler property)
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

	public static boolean enable()
	{
		return instance != null;
	}

	public static IPropertiesManager instance()
	{
		if (instance == null)
			instance = new IPropertiesManager();
		return instance;
	}

	@Nullable
	public IExtendedEntityProperties get(Entity entity, String id)
	{
		return entity.getExtendedProperties(id);
	}

	@Nullable
	public IStatus getStatus(Entity entity, String id)
	{
		IExtendedEntityProperties p = get(entity, id);
		if (p instanceof Status)
			return ((Status) p).real;
		return null;
	}

	@SubscribeEvent
	public void onEntityConstructing(EntityEvent.EntityConstructing event)
	{
		for (EntityHandler provider : map.get(event.entity.getClass()))
		{
			collection.start(event.entity);
			provider.handle(event.entity, collection);
			collection.end();
		}
	}
}
