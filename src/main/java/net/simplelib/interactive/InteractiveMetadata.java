package net.simplelib.interactive;

import api.simplelib.interactive.meta.InteractiveProperty;
import api.simplelib.interactive.Interactive;
import api.simplelib.interactive.meta.InteractivePropertyHook;
import api.simplelib.interactive.base.BaseHandler;
import api.simplelib.utils.GenericUtil;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import net.minecraft.world.World;
import net.simplelib.HelperMod;
import net.simplelib.common.nbt.ITagSerial;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.List;
import java.util.Map;

/**
 * @author ci010
 */
public class InteractiveMetadata//TODO finish this...
{
	private static Map<Class<? extends Interactive.Base>, BaseHandler> handlerMap;

	public static void registerBase(Class<? extends Interactive.Base> base, BaseHandler handler)
	{
		if (handlerMap == null)
			handlerMap = Maps.newHashMap();
		handlerMap.put(base, handler);
	}

	public static BaseHandler getBaseHandler(Interactive.Base base)
	{
		return handlerMap.get(base.getClass());
	}

	////////////////////////////

	private static Map<Class, Class<? extends InteractiveProperty>> metaDataConstructMap;//TODO handle this.

	public static void registerMeta(Class<? extends InteractiveProperty> meta)
	{
		System.out.println("register meta" + meta.getName());
		Type type = meta.getGenericInterfaces()[1];
		ParameterizedType t = (ParameterizedType) type;
		Type[] actualTypeArguments = t.getActualTypeArguments();
		Type data = actualTypeArguments[0], metaData = actualTypeArguments[1];
		Class intf = (Class) actualTypeArguments[2];
		for (Type interfaceType : intf.getGenericInterfaces())
		{
			if (interfaceType instanceof ParameterizedType)
			{
				t = (ParameterizedType) interfaceType;
				if (t.getRawType() == InteractivePropertyHook.class)
				{
					Type[] types = t.getActualTypeArguments();
					boolean b = types[0] == data && types[1] == metaData;
					if (!b)
						throw new IllegalArgumentException();
				}
			}
		}
		if (metaDataConstructMap == null)
			metaDataConstructMap = Maps.newHashMap();
		metaDataConstructMap.put(intf, meta);
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
		if (registerMap.containsKey(metadata.id))
		{
			HelperMod.LOG.fatal("The duplicated id!");
			throw new IllegalArgumentException("The duplicated id!");
		}
		registerMap.put(metadata.id, metadata);
	}

	////////////////////////////


	private String id;
	private ImmutableList<InteractiveProperty> metas;

	public <Meta, G extends InteractivePropertyHook<? extends ITagSerial, Meta>> Meta getMeta(Class<G> hook)
	{
		for (InteractiveProperty meta : metas)
			if (meta.getHook() == hook)
				return GenericUtil.cast(meta.getMeta());
		return null;
	}

	private InteractiveMetadata(Interactive provider)
	{
		this.id = provider.getId();

		Class<?>[] interfaces = provider.getClass().getInterfaces();

		ImmutableList.Builder<InteractiveProperty> builder = ImmutableList.builder();

		for (Class<?> anInterface : interfaces)
		{
			Class<? extends InteractiveProperty> aClass = metaDataConstructMap.get(anInterface);
			if (aClass != null)
			{
				try
				{
					InteractiveProperty meta = aClass.newInstance();
					if (!meta.init(provider))
						continue;
					builder.add(meta);
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
		}
		this.metas = builder.build();
	}


	public InteractiveEntity createEntity(World world)
	{
		ImmutableList.Builder<ITagSerial> builder = ImmutableList.builder();
		for (InteractiveProperty meta : metas)
		{
			ITagSerial data = meta.buildProperty();
			if (data == null)
				continue;
			builder.add(data);
		}
		return new InteractiveEntity(this.id, world, builder.build());
	}
}
