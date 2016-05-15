package net.simplelib.entity;

import api.simplelib.Instance;
import api.simplelib.registry.ModHandler;
import api.simplelib.utils.Nullable;
import api.simplelib.entity.EntityHandler;
import api.simplelib.entity.IStatus;
import com.google.common.collect.Lists;
import net.minecraft.entity.Entity;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.IExtendedEntityProperties;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.simplelib.common.CommonLogger;

import java.util.ArrayList;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * @author ci010
 */
@ModHandler
public class IPropertiesManager implements ITickable
{
	@Instance(weak = true)
	private static IPropertiesManager instance;

	private ArrayList<EntityHandler> handlers = Lists.newArrayList();
	private WeakHashMap<Entity, ITickable> updateWeakHashMap = new WeakHashMap<Entity, ITickable>();
	private StatusCollection collection = new StatusCollection(updateWeakHashMap);

	public void registerStatus(EntityHandler property)
	{
		CommonLogger.info("Register EntityHandler: [{}]", property.getClass());
		handlers.add(property);
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
		for (EntityHandler handler : this.handlers)
		{
			collection.start(event.entity);
			handler.handle(event.entity, collection);
			collection.end();
		}
	}

	@Override
	public void update()
	{
		for (Map.Entry<Entity, ITickable> entry : this.updateWeakHashMap.entrySet())
			if (entry.getKey().isEntityAlive())
				entry.getValue().update();
			else updateWeakHashMap.remove(entry.getKey());
	}
}
