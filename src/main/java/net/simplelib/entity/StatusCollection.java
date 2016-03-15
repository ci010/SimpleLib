package net.simplelib.entity;

import api.simplelib.entity.IStatus;
import api.simplelib.entity.EntityPropertyHook;
import net.minecraft.entity.Entity;
import net.minecraftforge.common.IExtendedEntityProperties;
import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraftforge.fml.common.eventhandler.Event;

/**
 * @author ci010
 */
public class StatusCollection implements EntityPropertyHook.Handler
{
	private Entity delegate;

	void set(Entity entity)
	{
		this.delegate = entity;
	}

	@Override
	public EntityPropertyHook.Handler add(String id, IExtendedEntityProperties properties)
	{
		delegate.registerExtendedProperties(id, properties);
		return this;
	}

	@Override
	public EntityPropertyHook.Handler add(String id, IStatus status)
	{
		Status real = new Status(id, status);
		delegate.registerExtendedProperties(id, real);
		return this;
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
}
