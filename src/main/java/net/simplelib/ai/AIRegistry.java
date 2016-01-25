package net.simplelib.ai;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.entity.EntityLiving;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.simplelib.common.registry.annotation.type.ModHandler;
import net.simplelib.common.utils.GenericUtil;

import java.util.Collection;

@ModHandler
public class AIRegistry
{
	private static Multimap<Class<? extends EntityLiving>, AIHandler> handlerMultimap = HashMultimap.create();
	private static AIHandler.AIManager cache = new AIHandler.AIManager();

	public static void registerHandler(AIHandler handler)
	{
		Class<? extends EntityLiving> clz = GenericUtil.getGenericTypeTo(handler);
		handlerMultimap.put(clz, handler);
	}

	@SubscribeEvent
	public void onEntityJoinWorld(EntityJoinWorldEvent e)
	{
		if (e.entity instanceof EntityLiving)
		{
			Class<? extends EntityLiving> clz = ((EntityLiving) e.entity).getClass();
			if (handlerMultimap.containsKey(clz))
			{
				EntityLiving living = (EntityLiving) e.entity;
				cache.start(living);
				Collection<AIHandler> handlers = handlerMultimap.get(clz);
				for (AIHandler handler : handlers)
					handler.handleAI(living, cache);
				cache.end();
			}
		}
	}
}
