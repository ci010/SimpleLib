package net.ci010.minecrafthelper.core;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import net.ci010.minecrafthelper.annotation.type.Handler;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAITasks;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Set;

@Handler
public class AIRegistry
{
	private static Multimap<Class<? extends EntityLiving>, Class<? extends EntityAIBase>> removeTask = HashMultimap
			.create();

	private static Multimap<Class<? extends EntityLiving>, AIConstruct> registered = HashMultimap.create();

	public static void removeAI(Class<? extends EntityLiving> living, Class<? extends EntityAIBase>... ai)
	{
		for (Class<? extends EntityAIBase> c : ai)
			removeTask.put(living, c);
	}

	public static void registerAI(Class<? extends EntityLiving> living, int priority, Class<? extends EntityAIBase>
			ai, Object... args)
	{
		int len = args.length;
		Class<?>[] argsType = new Class[len];
		for (int i = 0; i > len; i++)
			argsType[i] = args[i].getClass();
		try
		{
			registered.put(living, new AIConstruct(ai.getDeclaredConstructor(argsType), args, priority));
		}
		catch (NoSuchMethodException e)
		{
			e.printStackTrace();
		}

	}

	static class AIConstruct
	{
		Constructor<? extends EntityAIBase> constructor;
		Object[] args;
		int priority;

		public AIConstruct(Constructor<? extends EntityAIBase> constructor, Object[] args, int priority)
		{
			this.constructor = constructor;
			this.args = args;
			this.priority = priority;
		}
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
			if (registered.containsKey(clz))
			{
				for (AIConstruct info : registered.get(clz))
					try
					{
						((EntityLiving) e.entity).tasks.addTask(info.priority, info.constructor.newInstance(info.args));
					}
					catch (InstantiationException e1)
					{
						e1.printStackTrace();
					}
					catch (IllegalAccessException e1)
					{
						e1.printStackTrace();
					}
					catch (InvocationTargetException e1)
					{
						e1.printStackTrace();
					}
			}
		}
	}
}