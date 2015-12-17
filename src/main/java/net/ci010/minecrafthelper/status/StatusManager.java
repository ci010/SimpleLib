package net.ci010.minecrafthelper.status;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.ci010.minecrafthelper.annotation.type.Handler;
import net.ci010.minecrafthelper.util.GenericUtil;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.lang.reflect.Type;

/**
 * @author ci010
 */
@Handler
public class StatusManager
{
	private Multimap<Type, StatusProvider> map = HashMultimap.create();

	public void registerStatus(StatusProvider provider)
	{
		this.map.put(GenericUtil.getGenericType(provider), provider);
	}

	@SubscribeEvent
	public void onEntityConstructing(EntityEvent.EntityConstructing event)
	{
		for (StatusProvider provider : map.get(event.entity.getClass()))
		{
			Status s = provider.createStatus(event.entity);
			event.entity.registerExtendedProperties(s.getId(), s);
		}
	}
}
