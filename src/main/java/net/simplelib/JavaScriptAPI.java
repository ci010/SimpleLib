package net.simplelib;

import api.simplelib.ui.plugins.Plugin;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

import javax.script.*;
import java.io.Reader;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author ci010
 */
public class JavaScriptAPI
{
	private static Map<String, Class> eventMap = Maps.newHashMap();
	private static Map<String, Class> fullToClass = Maps.newHashMap();
	private static ScriptEngine js;

	static void build()
	{
		for (String s : ImmutableMap.copyOf(LibTransformer.eventMap).keySet())
		{
			try
			{
				Class<?> clz = Class.forName(s);
				if (Event.class.isAssignableFrom(clz))
				{
					fullToClass.put(clz.getName(), clz);
					eventMap.put(clz.getSimpleName(), clz);
				}
			}
			catch (ClassNotFoundException e)
			{
				e.printStackTrace();
			}
		}

		ScriptEngineManager manager = new ScriptEngineManager();
		js = manager.getEngineByExtension("js");
		SimpleBindings eventBinding = new SimpleBindings();
		eventBinding.putAll(eventMap);
		eventBinding.putAll(fullToClass);
		eventBinding.put("bus", new Bus());
		for (EventPriority eventPriority : EventPriority.values())
			eventBinding.put(eventPriority.name(), eventBinding);

		js.setBindings(eventBinding, ScriptContext.ENGINE_SCOPE);
	}

	public Class<? extends Event> get(String name)
	{
		if (eventMap.containsKey(name))
			return eventMap.get(name);
		if (fullToClass.containsKey(name))
			return fullToClass.get(name);
		return null;
	}

	public static Object eval(Reader reader) throws ScriptException
	{
		if (js == null)
			return null;
		return js.eval(reader);
	}

	public static Object eval(String string) throws ScriptException
	{
		if (js == null)
			return null;
		return js.eval(string);
	}

	public static boolean isAvailable()
	{
		return js != null;
	}

	public static Plugin evalPlugin(Reader reader)
	{
		try
		{
			js.eval(reader);
		}
		catch (ScriptException e)
		{
			e.printStackTrace();
		}
		Plugin plugin = ((Invocable) js).getInterface(Plugin.class);
		if (plugin != null)
			return plugin;
		return null;
	}

	public Plugin evalPlugin(String s)
	{
		try
		{
			js.eval(s);
		}
		catch (ScriptException e)
		{
			e.printStackTrace();
		}
		Plugin plugin = ((Invocable) js).getInterface(Plugin.class);
		if (plugin != null)
			return plugin;
		return null;
	}

	public static class Bus
	{
		private final int busID = ReflectionHelper.getPrivateValue(EventBus.class, MinecraftForge.EVENT_BUS, "busID");

		private ConcurrentHashMap<Object, ArrayList<IEventListener>> listeners = new ConcurrentHashMap<Object, ArrayList<IEventListener>>();

		public void register(Object thiz, Class type, IEventListener listener)
		{
			register(thiz, type, EventPriority.NORMAL, listener);
		}

		public void register(Object thiz, Class type, EventPriority priority, IEventListener listener)
		{
			try
			{
				Constructor<?> ctr = type.getConstructor();
				ctr.setAccessible(true);
				Event event = (Event) ctr.newInstance();
				event.getListenerList().register(busID, priority, listener);

				ArrayList<IEventListener> others = listeners.get(thiz);
				if (others == null)
				{
					others = new ArrayList<IEventListener>();
					listeners.put(thiz, others);
				}
				others.add(listener);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}

		public void unregister(Object object)
		{
			ArrayList<IEventListener> list = listeners.remove(object);
			if (list == null)
				return;
			for (IEventListener listener : list)
				ListenerList.unregisterAll(busID, listener);
		}
	}
}
