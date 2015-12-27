package net.simplelib.registry;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAITasks;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.simplelib.annotation.type.Handler;
import net.simplelib.util.GenericUtil;

import java.util.Set;

@Handler
public class AIRegistry
{
	private static Multimap<Class<? extends EntityLiving>, Class<? extends EntityAIBase>> removeTask = HashMultimap
			.create();

	private static Multimap<Class<? extends EntityLiving>, AIProvider> register = HashMultimap.create();

	public static void removeAI(Class<? extends EntityLiving> living, Class<? extends EntityAIBase>... ai)
	{
		for (Class<? extends EntityAIBase> c : ai)
			removeTask.put(living, c);
	}

	public static void registerAI(AIProvider provider)
	{
		Class<? extends EntityLiving> clz = GenericUtil.getGenericTypeTo(provider);
		register.put(clz, provider);
	}

	@SubscribeEvent
	public void onEntityJoinWorld(EntityJoinWorldEvent e)
	{
		if (e.entity instanceof EntityLiving)
		{
			Class<? extends EntityLiving> clz = ((EntityLiving) e.entity).getClass();
			if (removeTask.containsKey(clz))
			{
				EntityLiving living = (EntityLiving) e.entity;
				Set<EntityAIBase> removed = Sets.newHashSet();
				for (Class<? extends EntityAIBase> r : removeTask.values())
					for (Object obj : living.tasks.taskEntries)
					{
						EntityAITasks.EntityAITaskEntry entry = (EntityAITasks.EntityAITaskEntry) obj;
						if (entry.action.getClass() == r)
							removed.add(entry.action);
					}
				for (EntityAIBase ai : removed)
					living.tasks.removeTask(ai);
			}
			if (register.containsKey(clz))
				for (AIProvider provider : register.get(clz))
					((EntityLiving) e.entity).tasks.addTask(provider.priority(), provider.createAI((EntityLiving) e.entity));
		}
	}
}
