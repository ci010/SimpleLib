package net.simplelib.entity;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.common.IExtendedEntityProperties;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.simplelib.common.CommonLogger;
import net.simplelib.common.registry.annotation.field.Instance;
import net.simplelib.common.registry.annotation.type.ModHandler;

/**
 * @author ci010
 */
@ModHandler
public class IPropertiesManager
{
	@Instance(weak = true)
	private static IPropertiesManager instance;

	private Multimap<Class<? extends Entity>, IPropertiesHandler> map = HashMultimap.create();
	private IPropertiesHandler.StatusCollection collection = new IPropertiesHandler.StatusCollection();

	public void registerStatus(Class<? extends Entity> clz, IPropertiesHandler provider)
	{
		CommonLogger.info("Register the status handler to all {}.", clz.getSimpleName());
		if (clz == EntityPlayer.class)
		{
			map.put(EntityPlayerSP.class, provider);
			map.put(EntityPlayerMP.class, provider);
			return;
		}
		map.put(clz, provider);
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
		for (IPropertiesHandler provider : map.get(event.entity.getClass()))
		{
			collection.set(event.entity);
			provider.handle(collection);
		}
	}
}
