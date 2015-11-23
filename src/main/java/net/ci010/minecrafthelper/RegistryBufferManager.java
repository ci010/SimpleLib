package net.ci010.minecrafthelper;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.ci010.minecrafthelper.abstracts.RegistryDelegate;
import net.ci010.minecrafthelper.annotation.type.ASMDelegate;
import net.ci010.minecrafthelper.util.ASMDataUtil;
import net.ci010.minecrafthelper.util.GenericUtil;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.discovery.ASMDataTable;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;

/**
 * @author ci010
 */
public class RegistryBufferManager
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
		RegistryDelegate obj;
		Method method;

		public SubscriberInfo(RegistryDelegate obj, Method method)
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

	final void load(ASMDataTable table)
	{
		for (ASMDataTable.ASMData data : table.getAll(ASMDelegate.class.getName()))
		{
			Class<?> registryDelegateType = ASMDataUtil.getClass(data);

			if (!RegistryDelegate.class.isAssignableFrom(registryDelegateType))
				throw new UnsupportedOperationException("The class need to extends RegistryDelegate");
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
					Class<? extends RegistryDelegate> real = GenericUtil.cast(registryDelegateType);
					RegistryDelegate delegate = real.newInstance();
					Class<? extends Annotation> target = GenericUtil.getGenericTypeTo(delegate);
					for (ASMDataTable.ASMData meta : table.getAll(target.getName()))
					{
						delegate.addCache(meta);
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

	void invoke(FMLStateEvent state)
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
			subscriberInfoMap.removeAll(FMLConstructionEvent.class);
			subscriberInfoMap.removeAll(FMLPreInitializationEvent.class);
			subscriberInfoMap.removeAll(FMLInitializationEvent.class);
			subscriberInfoMap.removeAll(FMLPostInitializationEvent.class);
			subscriberInfoMap.removeAll(FMLLoadCompleteEvent.class);
		}
	}
}
