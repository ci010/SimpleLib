package net.simplelib.interactive;

import api.simplelib.Context;
import api.simplelib.interactive.Interactive;
import api.simplelib.interactive.base.wrapper.BaseHandler;
import api.simplelib.interactive.meta.InteractiveProperty;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.simplelib.HelperMod;
import api.simplelib.utils.ITagSerializable;

import java.util.List;
import java.util.Map;

/**
 * @author ci010
 */
public class InteractiveMetadata implements Interactive.MetaData
{
	private static Map<Class<? extends Interactive.Base>, BaseHandler> handlerMap;

	public static void registerBase(Class<? extends Interactive.Base> base, BaseHandler handler)
	{
		if (handlerMap == null)
			handlerMap = Maps.newHashMap();
		handlerMap.put(base, handler);
	}

	////////////////////////////

	private static Map<Class, InteractiveProperty> metaDataConstructMap;//TODO handle this.

	public static void registerMeta(Class<? extends InteractiveProperty> meta)
	{
		System.out.println("register meta" + meta.getName());
		if (metaDataConstructMap == null)
			metaDataConstructMap = Maps.newHashMap();
		try
		{
			InteractiveProperty metaInstance = meta.newInstance();
			metaInstance.build();
			metaDataConstructMap.put(metaInstance.interfaceType(), metaInstance);
		}
		catch (InstantiationException e)
		{
			e.printStackTrace();
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
	}


	////////////////////////////
	private static Map<String, InteractiveMetadata> registerMap = Maps.newHashMap();

	public static InteractiveMetadata getInstance(String id)
	{
		return registerMap.get(id);
	}

	public static void register(Interactive interactive)
	{
		InteractiveMetadata metadata = new InteractiveMetadata(interactive);
		interactive.build();
		interactive.getBase().setup(interactive, metadata);
//		handlerMap.get(interactive.getBase().getClass()).setup(interactive, metadata);
		if (registerMap.containsKey(interactive.getId()))
		{
			HelperMod.LOG.fatal("The duplicated id!");
			throw new IllegalArgumentException("The duplicated id!");
		}
		registerMap.put(interactive.getId(), metadata);
	}

	////////////////////////////


	private ImmutableList<InteractiveProperty.Worker> workers;
	private String id;

	private InteractiveMetadata(Interactive provider)
	{
		this.id = provider.getId();

		Class<?>[] interfaces = provider.getClass().getInterfaces();

		ImmutableList.Builder<InteractiveProperty.Worker> builder = ImmutableList.builder();
		for (Class<?> anInterface : interfaces)
		{
			InteractiveProperty meta = metaDataConstructMap.get(anInterface);
			if (meta != null)
			{
				InteractiveProperty.Worker worker = meta.newWorker();
				if (worker.init(provider))
					builder.add(worker);
			}
		}
		this.workers = builder.build();
	}


	public Interactive.Entity createEntity(Context context)
	{
		List<ITagSerializable> builder = Lists.newArrayList();
		List<ICapabilityProvider> lst = Lists.newArrayList();
		InteractiveEntity entity = new InteractiveEntity(this.id, context.getWorld());
		for (InteractiveProperty.Worker worker : workers)
		{
			ITagSerializable data = worker.buildProperty(entity);
			if (data == null)
				continue;
			builder.add(data);
			if (data instanceof ICapabilityProvider)
				lst.add((ICapabilityProvider) data);
		}
		return entity.load(
				builder.toArray(new ITagSerializable[builder.size()])
				, lst.toArray(new ICapabilityProvider[lst.size()]));
	}

	@Override
	public String id()
	{
		return this.id;
	}

	@Override
	public ImmutableList<InteractiveProperty.Worker> workers()
	{
		return this.workers;
	}
}
