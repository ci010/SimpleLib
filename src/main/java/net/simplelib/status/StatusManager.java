package net.simplelib.status;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import net.minecraft.entity.Entity;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.simplelib.annotation.type.Handler;
import net.simplelib.util.GenericUtil;

import java.lang.reflect.Type;
import java.util.Map;

/**
 * @author ci010
 */
@Handler
public class StatusManager
{
	private static Multimap<Type, StatusProvider> map = HashMultimap.create();
	private static Map<Class<? extends StatusProvider>, StatusProvider> indexMap = Maps.newHashMap();

	public static void registerStatus(StatusProvider provider)
	{
		indexMap.put(provider.getClass(), provider);
		map.put(GenericUtil.getGenericType(provider), provider);
	}

	/**
	 * Get the status relates to the provider.
	 *
	 * @param entity The entity will be extracted status from.
	 * @param clz    The class of status provider.
	 * @param <E>    The type of entity.
	 * @param <S>    The type of status.
	 * @return The status.
	 */
	public static <E extends Entity, S extends Status> S getStatus(E entity, Class<? extends StatusProvider<E, S>> clz)
	{
		return GenericUtil.cast(entity.getExtendedProperties(indexMap.get(clz).getId()));
	}

	@SubscribeEvent
	public void onEntityConstructing(EntityEvent.EntityConstructing event)
	{
		for (StatusProvider provider : map.get(event.entity.getClass()))
		{
			Status s = provider.createStatus(event.entity);
			s.id = provider.getId();
			event.entity.registerExtendedProperties(s.id, s);
		}
	}
}
