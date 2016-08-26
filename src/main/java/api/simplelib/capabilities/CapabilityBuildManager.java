package api.simplelib.capabilities;

import api.simplelib.Instance;
import api.simplelib.LoadingDelegate;
import api.simplelib.ResourceLocations;
import api.simplelib.data.DataEntry;
import api.simplelib.registry.ASMRegistryDelegate;
import api.simplelib.registry.ModHandler;
import api.simplelib.utils.Consumer;
import api.simplelib.utils.DebugLogger;
import api.simplelib.utils.TypeUtils;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.commons.lang3.tuple.Pair;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author ci010
 */
@ModHandler
public class CapabilityBuildManager
{
	@Instance
	private static CapabilityBuildManager instance = new CapabilityBuildManager();

	private Map<Class, CapabilityBuilderHandler<?>> paramToBuilder = Maps.newHashMap();
	private Map<String, CapabilityBuilderHandler.JsonBase<?>> nameToBuilder = Maps.newHashMap();

	private Map<Class, SubscribeInfo> watchedToMethods = Maps.newHashMap();
	private DataEntry<ResourceLocation> entry;//TODO create this

	private Multimap<ResourceLocation, Pair<CapabilityBuilderHandler, Consumer[]>> compiledMap = HashMultimap
			.create();

	public static CapabilityBuildManager instance()
	{
		return instance;
	}

	public <T> CapabilityBuilderHandler<T> getBuilderHandler(Class<T> builderType)
	{
		return TypeUtils.cast(paramToBuilder.get(builderType));
	}

	public Map<ResourceLocation, ICapabilityProvider> handle(Object owner)
	{
		Map<ResourceLocation, ICapabilityProvider> map = Maps.newHashMap();
		handleRuntime(map, owner, owner);
		return map;
	}

	public void recompile(ResourceLocation location)
	{
		compiledMap.removeAll(location);
		tryCompile(location);
	}

	private void revolveImplicit(Class clz, List<Method> cacheMethod, List<CapabilityBuilderHandler<?>> cacheCap)
	{
		Map<Class, SubscribeInfo> watchedToMethods = CapabilityBuildManager.instance.watchedToMethods;
		Map<Class, CapabilityBuilderHandler<?>> paramToBuilder = CapabilityBuildManager.instance.paramToBuilder;
		for (Method method : clz.getMethods())
			if (method.isAnnotationPresent(CapabilityBuild.class))
			{
				if (Modifier.isStatic(method.getModifiers()) || !Modifier.isPublic(method.getModifiers()))
				{
					DebugLogger.fatal("Capability build method should be public & non-static. Cannot revolve {} " +
							"method.", method);
					continue;
				}
				Class<?>[] types = method.getParameterTypes();
				if (types.length != 1)
				{
					DebugLogger.fatal("Capability build ");
					continue;
				}
				if (!paramToBuilder.containsKey(types[0]))
				{
					DebugLogger.fatal("");
					continue;
				}
				cacheMethod.add(method);
				cacheCap.add(paramToBuilder.get(types[0]));
			}

		watchedToMethods.put(clz, new SubscribeInfo(cacheMethod.toArray(new Method[cacheMethod.size()]),
				cacheCap.toArray(new CapabilityBuilderHandler[cacheCap.size()])));
	}

	private void handleCompiled(Map<ResourceLocation, ICapabilityProvider> map, Object owner)
	{
		ResourceLocation loc = ResourceLocations.inspect(owner);
		if (loc == null)
			return;

		if (compiledMap.containsKey(loc))
		{
			Collection<Pair<CapabilityBuilderHandler, Consumer[]>> pairs = compiledMap.get(loc);
			if (pairs.isEmpty())
				return;
			for (Pair<CapabilityBuilderHandler, Consumer[]> pair : pairs)
			{
				Object builder = pair.getLeft().createBuilder(owner);
				for (Consumer consumer : pair.getRight())
					consumer.accept(builder);
				if (!map.containsKey(pair.getLeft().getName()))
					map.put(pair.getLeft().getName(), pair.getLeft().build(builder, owner));
			}
		}
		else tryCompile(loc);
	}

	private void handleRuntime(Map<ResourceLocation, ICapabilityProvider> map, Object owner, Object obj)
	{
		Class clz = obj.getClass();
		SubscribeInfo info = watchedToMethods.get(clz);
		if (info == null)
			revolveImplicit(clz, Lists.<Method>newArrayList(), Lists.<CapabilityBuilderHandler<?>>newArrayList());
		info = watchedToMethods.get(clz);
		if (info == null)
			return;
		for (int i = 0; i < info.size; i++)
		{
			Method method = info.m[i];
			Object build = info.handlers[i].createBuilder(owner);
			if (build == null)
			{
				DebugLogger.fatal("The handler {} does not handle {} as context! It will not provider capability!",
						info.handlers[i], owner);
				continue;
			}
			try
			{
				method.invoke(obj, build);
			}
			catch (IllegalAccessException e)
			{
				e.printStackTrace();
			}
			catch (InvocationTargetException e)
			{
				e.printStackTrace();
			}
			ResourceLocation name = info.handlers[i].getName();
			if (!map.containsKey(name))
				map.put(name, info.handlers[i].build(build, owner));
		}
	}

	private CapabilityLocation[] getRealLocations(ResourceLocation location)
	{
		Set<String> set = nameToBuilder.keySet();
		CapabilityLocation[] resourceLocations = new CapabilityLocation[set.size()];
		int idx = 0;
		for (String s : set)
			resourceLocations[idx++] = new CapabilityLocation(location, s);
		return resourceLocations;
	}

	private class CapabilityLocation extends ResourceLocation
	{
		private final String capabilityType;

		public CapabilityLocation(ResourceLocation location, String capabilityType)
		{
			super(location.getResourceDomain(), location.getResourcePath() + "/" + location.getResourcePath() + ".json");
			this.capabilityType = capabilityType;
		}

		public String getCapabilityType()
		{
			return capabilityType;
		}
	}

	private void tryCompile(ResourceLocation location)
	{
		CapabilityLocation[] realLocations = getRealLocations(location);
		GsonBuilder builder = new GsonBuilder();
		for (CapabilityLocation realLocation : realLocations)
		{
			if (realLocation == null)
				continue;
			CapabilityBuilderHandler.JsonBase<?> jsonBase = nameToBuilder.get(realLocation.getCapabilityType());
			builder.registerTypeAdapter(Consumer[].class, jsonBase.getDeserializer());
		}
		Gson gson = builder.create();
		for (CapabilityLocation realLocation : realLocations)
		{
//			InputStream[] allResources = entry.getAllResources(realLocation);
//			if (allResources == null)
//				continue;
//			for (int i = allResources.length - 1; i >= 0; i--)
//			{
//				try
//				{
//					InputStream stream = allResources[i];
//					Consumer[] o = gson.fromJson(new InputStreamReader(stream), Consumer[].class);
//					CapabilityBuilderHandler jsonBase = nameToBuilder.get(realLocation.getCapabilityType());
//					compiledMap.put(location, Pair.of(jsonBase, o));
//					stream.close();
//				}
//				catch (IOException e)
//				{
//					e.printStackTrace();
//				}
//			}
		}
	}

	private CapabilityBuildManager() {}

	@SubscribeEvent
	public void onAttach(AttachCapabilitiesEvent event)
	{
		Map<ResourceLocation, ICapabilityProvider> map = Maps.newHashMap();
		if (event instanceof AttachCapabilitiesEvent.Item)
			handleRuntime(map, event.getObject(), ((AttachCapabilitiesEvent.Item) event).getItemStack());
		else if (event instanceof AttachCapabilitiesEvent.Entity)
			handleRuntime(map, event.getObject(), ((AttachCapabilitiesEvent.Entity) event).getEntity());
		else if (event instanceof AttachCapabilitiesEvent.TileEntity)
			handleRuntime(map, event.getObject(), ((AttachCapabilitiesEvent.TileEntity) event).getTileEntity());
		else handleRuntime(map, event.getObject(), event.getObject());
		handleCompiled(map, event.getObject());
		for (Map.Entry<ResourceLocation, ICapabilityProvider> entry : map.entrySet())
			event.addCapability(entry.getKey(), entry.getValue());
	}

	@LoadingDelegate
	public static class PreReg extends ASMRegistryDelegate<ModCapabilityBuilderHandler>
	{
		@Mod.EventHandler
		public void pre(FMLPreInitializationEvent event)
		{
			Map<Class, CapabilityBuilderHandler<?>> paramToBuilder
					= CapabilityBuildManager.instance.paramToBuilder;
			Class<?> providerClass = this.getAnnotatedClass();
			if (CapabilityBuilderHandler.class.isAssignableFrom(providerClass))
			{
				Class paramType = (Class) TypeUtils.getInterfaceGenericType(providerClass, CapabilityBuilderHandler.class, 0);
				try
				{
					if (paramToBuilder.containsKey(paramType))
					{
						DebugLogger.fatal("Duplicated CapabilityBuild class has been register! The handler {} won't " +
								"be registered with {}!", providerClass, paramType);
						return;
					}
					CapabilityBuilderHandler<?> cast = TypeUtils.cast(providerClass
							.newInstance());
					if (cast instanceof CapabilityBuilderHandler.JsonBase)
						CapabilityBuildManager.instance.nameToBuilder.put(cast.getName().getResourcePath(), (CapabilityBuilderHandler.JsonBase<?>) cast);
					paramToBuilder.put(paramType, cast);
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
	}

	@LoadingDelegate
	public static class PostReg extends ASMRegistryDelegate<CapabilityBuild>
	{
		private List<Method> cacheMethod = Lists.newArrayList();
		private List<CapabilityBuilderHandler<?>> cacheCap = Lists.newArrayList();

		@Mod.EventHandler
		public void post(FMLPostInitializationEvent event)
		{
			cacheCap.clear();
			cacheMethod.clear();
			if (!CapabilityBuildManager.instance.watchedToMethods.containsKey(this.getAnnotatedClass()))
				CapabilityBuildManager.instance.revolveImplicit(this.getAnnotatedClass(), cacheMethod, cacheCap);
		}
	}

	private static class SubscribeInfo
	{
		Method[] m;
		CapabilityBuilderHandler<Object>[] handlers;
		int size;

		public SubscribeInfo(Method[] m, CapabilityBuilderHandler[] arg)
		{
			if (m.length != arg.length)
				throw new IllegalArgumentException();
			this.m = m;
			this.handlers = arg;
			this.size = m.length;

		}
	}
}
