package net.simplelib.entity;

import api.simplelib.entity.IStatus;
import api.simplelib.entity.PropertyHook;
import net.minecraft.entity.Entity;
import net.minecraftforge.common.IExtendedEntityProperties;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraftforge.fml.common.eventhandler.Event;

/**
 * @author ci010
 */
public class StatusCollection implements PropertyHook.Handler
{
	private Entity delegate;

//	public StatusCollection add(String id, IExtendedEntityProperties stpropertiestus)
//	{
//		if (stpropertiestus instanceof Status)
//		{
//			Status s = (Status) stpropertiestus;
//			s.id = id;
//		}
//		ExtendPropertyAddEvent event = new ExtendPropertyAddEvent(delegate, id, stpropertiestus);
//		boolean canceled = MinecraftForge.EVENT_BUS.post(event);
//		if (!canceled)
//			event.entity.registerExtendedProperties(event.id, event.status);
//		return this;
//	}

	void set(Entity entity)
	{
		this.delegate = entity;
	}

	@Override
	public PropertyHook.Handler add(String id, IExtendedEntityProperties properties)
	{
		delegate.registerExtendedProperties(id, properties);
		return this;
	}

	@Override
	public PropertyHook.Handler add(String id, IStatus status)
	{
		delegate.registerExtendedProperties(id, new Status(status));
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
