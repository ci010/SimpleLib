package net.simplelib.common.registry;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.discovery.ASMDataTable;
import net.minecraftforge.fml.common.event.*;
import net.simplelib.common.registry.abstracts.ASMRegistryDelegate;
import net.simplelib.common.registry.annotation.type.ASMDelegate;
import net.simplelib.common.utils.ASMDataUtil;
import net.simplelib.common.utils.GenericUtil;
import net.simplelib.common.utils.PackageModIdMap;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.Map;

/**
 * @author ci010
 */
public final class RegistryBufferManager
{
	private static RegistryBufferManager instance;

	public static RegistryBufferManager instance()
	{
		if (instance == null) instance = new RegistryBufferManager();
		return instance;
	}

	public static void close()
	{
		instance = null;
	}

	private Multimap<Class<? extends FMLStateEvent>, SubscriberInfo> subscriberInfoMap = HashMultimap.create();

	class SubscriberInfo
	{
		ASMRegistryDelegate obj;
		Method method;

		public SubscriberInfo(ASMRegistryDelegate obj, Method method)
		{
			this.obj = obj;
			this.method = method;
		}

		@Override
		public String toString()
		{
			return obj.getClass().getName().concat(" ").concat(method.getName());
		}
	}

	private PackageModIdMap rootMap = new PackageModIdMap();

	public final void load(ASMDataTable table)
	{
		for (ASMDataTable.ASMData data : table.getAll(Mod.class.getName()))
			rootMap.put(ASMDataUtil.getClass(data).getPackage()
					.getName(), data.getAnnotationInfo().get("modid").toString());
		for (ASMDataTable.ASMData data : table.getAll(ASMDelegate.class.getName()))
		{
			Class<?> registryDelegateType = ASMDataUtil.getClass(data);

			if (!ASMRegistryDelegate.class.isAssignableFrom(registryDelegateType))
				throw new UnsupportedOperationException("The class need to extends ASMRegistryDelegate");
			if (!(registryDelegateType.getGenericSuperclass() instanceof ParameterizedType))
				throw new UnsupportedOperationException("The class need to handle an annotation type");


			for (Method method : registryDelegateType.getDeclaredMethods())
			{
				if (!method.isAnnotationPresent(Mod.EventHandler.class))
					continue;

				if (method.getParameterTypes().length != 1)
					throw new UnsupportedOperationException("The method being subscribed need to have ONLY one parameter!");

				Class<?> methodParam = method.getParameterTypes()[0];
				if (!FMLStateEvent.class.isAssignableFrom(methodParam))
					throw new UnsupportedOperationException("The method's parameter must be a FMLStateEvent but not "
							.concat(methodParam.getName()));
				if (methodParam == FMLServerStoppedEvent.class && methodParam == FMLServerStoppingEvent.class)
					throw new UnsupportedOperationException("Not support FMLServerStoppedEvent and FMLServerStoppingEvent");

				try
				{
					Class<? extends FMLStateEvent> state = GenericUtil.cast(methodParam);
					Class<? extends ASMRegistryDelegate> real = GenericUtil.cast(registryDelegateType);
					ASMRegistryDelegate delegate = real.newInstance();
					Class<? extends Annotation> target = GenericUtil.getGenericTypeTo(delegate);
					for (ASMDataTable.ASMData meta : table.getAll(target.getName()))
					{
						delegate.addCache(rootMap.getModid(ASMDataUtil.getClass(meta).getPackage()
								.getName()), meta);
						subscriberInfoMap.put(state, new SubscriberInfo(delegate, method));
					}
				}
				catch (InstantiationException e)
				{
					//TODO log
					e.printStackTrace();
				}
				catch (IllegalAccessException e)
				{
					e.printStackTrace();
				}
			}
		}
	}

	public void invoke(FMLStateEvent state)
	{
		Class<? extends FMLStateEvent> realType = state.getClass();

		for (SubscriberInfo info : subscriberInfoMap.get(realType))
			try
			{
				while (info.obj.hasNext())
				{
					info.obj.next();
					info.method.invoke(info.obj, state);
				}
			}
			catch (IllegalAccessException e)
			{
				e.printStackTrace();
			}
			catch (InvocationTargetException e)
			{
				System.out.println(info);
				e.printStackTrace();
			}

		if (state instanceof FMLLoadCompleteEvent)
		{
			rootMap = null;
			subscriberInfoMap.removeAll(FMLConstructionEvent.class);
			subscriberInfoMap.removeAll(FMLPreInitializationEvent.class);
			subscriberInfoMap.removeAll(FMLInitializationEvent.class);
			subscriberInfoMap.removeAll(FMLPostInitializationEvent.class);
			subscriberInfoMap.removeAll(FMLLoadCompleteEvent.class);
		}
	}
}
