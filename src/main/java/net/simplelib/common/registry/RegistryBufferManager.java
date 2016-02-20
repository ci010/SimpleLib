package net.simplelib.common.registry;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.discovery.ASMDataTable;
import net.minecraftforge.fml.common.event.*;
import api.simplelib.registry.ASMRegistryDelegate;
import net.simplelib.common.registry.annotation.type.ASMDelegate;
import api.simplelib.utils.ASMDataUtil;
import api.simplelib.utils.GenericUtil;
import api.simplelib.utils.PackageModIdMap;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;

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
		Object obj;
		Method method;

		public SubscriberInfo(Object obj, Method method)
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
			boolean asmType;

			if (asmType = ASMRegistryDelegate.class.isAssignableFrom(registryDelegateType))
				if (!(registryDelegateType.getGenericSuperclass() instanceof ParameterizedType))
					throw new UnsupportedOperationException("The ASMRegistryDelegate class need to handle an " +
							"annotation type");

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
					Object delegate = registryDelegateType.newInstance();
					if (asmType)
					{
						ASMRegistryDelegate asmDelegate = (ASMRegistryDelegate) delegate;
						Class<? extends Annotation> target = GenericUtil.getGenericTypeTo(asmDelegate);
						for (ASMDataTable.ASMData meta : table.getAll(target.getName()))
						{
							Package pkg = ASMDataUtil.getClass(meta).getPackage();
							String modid = rootMap.getModid(pkg.getName());
							if (modid == null)
							{
								modid = "Anonymous";
//							HelperMod.metadata.childMods.add();
							}
							asmDelegate.addCache(modid, meta);
						}
					}
					subscriberInfoMap.put(state, new SubscriberInfo(delegate, method));
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
				if (info.obj instanceof ASMRegistryDelegate)
				{
					ASMRegistryDelegate asmRegistryDelegate = (ASMRegistryDelegate) info.obj;
					while (asmRegistryDelegate.hasNext())
					{
						asmRegistryDelegate.next();
						info.method.invoke(info.obj, state);
					}
				}
				else
					info.method.invoke(info.obj, state);
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
			ASMDataUtil.clear();
			subscriberInfoMap.removeAll(FMLConstructionEvent.class);
			subscriberInfoMap.removeAll(FMLPreInitializationEvent.class);
			subscriberInfoMap.removeAll(FMLInitializationEvent.class);
			subscriberInfoMap.removeAll(FMLPostInitializationEvent.class);
			subscriberInfoMap.removeAll(FMLLoadCompleteEvent.class);
		}
	}
}
