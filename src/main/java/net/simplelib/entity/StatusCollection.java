package net.simplelib.entity;

import api.simplelib.entity.EntityHandler;
import api.simplelib.entity.IStatus;
import api.simplelib.entity.IStatusUpdate;
import com.google.common.collect.Sets;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAITasks;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.IExtendedEntityProperties;
import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraftforge.fml.common.eventhandler.Event;

import java.util.Set;
import java.util.WeakHashMap;

/**
 * @author ci010
 */
public class StatusCollection implements EntityHandler.Manager
{
	private AIManagerImpl ai = new AIManagerImpl();
	private Entity delegate;
	private boolean hasAI;
	private WeakHashMap<Entity, ITickable> updateWeakHashMap;

	StatusCollection(WeakHashMap<Entity, ITickable> updateWeakHashMap)
	{
		this.updateWeakHashMap = updateWeakHashMap;
	}

	void start(Entity entity)
	{
		this.delegate = entity;
		if (entity instanceof EntityLiving)
		{
			hasAI = true;
			ai.start((EntityLiving) entity);
		}
	}

	void end()
	{
		if (hasAI)
			ai.end();
	}

	@Override
	public EntityHandler.Manager add(String id, IExtendedEntityProperties properties)
	{
		delegate.registerExtendedProperties(id, properties);
		return this;
	}

	@Override
	public EntityHandler.Manager add(String id, IStatus status)
	{
		Status real = new Status(id, status);
		delegate.registerExtendedProperties(id, real);
		if (status instanceof ITickable)
			updateWeakHashMap.put(this.delegate, (ITickable) status);
		return this;
	}

	@Override
	public EntityHandler.AIManager getAIManager()
	{
		if (hasAI)
			return ai;
		return null;
	}

	@Cancelable
	class ExtendPropertyAddEvent extends Event
	{
		public Entity entity;
		public String id;
		public IExtendedEntityProperties status;

		public ExtendPropertyAddEvent(Entity entity, String id, IExtendedEntityProperties status)
		{
			this.entity = entity;
			this.id = id;
			this.status = status;
		}
	}

	public class AIManagerImpl implements EntityHandler.AIManager
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
			if (this.delegate == null)
				return;
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
			this.delegate = null;
		}

		void start(EntityLiving base)
		{
			delegate = base.tasks;
		}
	}
}
