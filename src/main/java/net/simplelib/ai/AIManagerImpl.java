package net.simplelib.ai;

import api.simplelib.ai.AIManager;
import com.google.common.collect.Sets;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAITasks;

import java.util.Set;

/**
 * @author ci010
 */
public class AIManagerImpl implements AIManager
{
	private EntityAITasks delegate;
	private Set<Class<? extends EntityAIBase>> removedCache = Sets.newHashSet();
	private Set<EntityAIBase> removed = Sets.newHashSet();

	public AIManagerImpl addAI(int priority, EntityAIBase ai)
	{
		delegate.addTask(priority, ai);
		return this;
	}

	public AIManagerImpl removeAI(Class<? extends EntityAIBase> clz)
	{
		removedCache.add(clz);
		return this;
	}

	public AIManagerImpl removeAI(int num)
	{
		delegate.taskEntries.remove(num);
		return this;
	}

	void end()
	{
		for (Class<? extends EntityAIBase> r : removedCache)
			for (Object obj : delegate.taskEntries)
			{
				EntityAITasks.EntityAITaskEntry entry = (EntityAITasks.EntityAITaskEntry) obj;
				if (entry.action.getClass() == r)
					removed.add(entry.action);
			}
		for (EntityAIBase ai : removed)
			delegate.removeTask(ai);
		removed.clear();
		removedCache.clear();
	}

	void start(EntityLiving base)
	{
		delegate = base.tasks;
	}
}
