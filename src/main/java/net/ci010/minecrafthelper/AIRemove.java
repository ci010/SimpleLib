package net.ci010.minecrafthelper;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import net.ci010.minecrafthelper.annotation.type.Handler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAITasks;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Set;

@Handler
public class AIRemove
{
	private static Multimap<Class<? extends EntityLiving>, Class<? extends EntityAIBase>> removeTask = HashMultimap
			.create();

	public static void removeAI(Class<? extends EntityLiving> living, Class<? extends EntityAIBase>... ai)
	{
		for (Class<? extends EntityAIBase> c : ai)
			removeTask.put(living, c);
	}

	@SubscribeEvent
	public void onEntityJoinWorld(EntityJoinWorldEvent e)
	{
		if (e.entity instanceof EntityLiving)
		{
			Class<? extends Entity> clz = e.entity.getClass();
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
		}
	}
}