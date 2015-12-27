package net.simplelib;

import com.google.common.collect.Maps;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sun.reflect.Reflection;

import java.util.Map;

//import sun.reflect.Reflection;

/**
 * @author ci010
 */
public class CommonLogger
{
	static Map<String, Logger> pkgMap = Maps.newHashMap();

	static void init()
	{
		for (ModContainer mod : Loader.instance().getActiveModList())
		{
			String modid = mod.getModId();
			if (modid.equals("FML") || modid.equals("mcp") || modid.equals("Forge"))
				continue;
			Logger log = LogManager.getLogger(mod.getModId());
			for (String s : mod.getOwnedPackages())
				if (!pkgMap.containsKey(s))
					pkgMap.put(s, log);
		}
	}

	private static Logger getLogger(Class<?> clz)
	{
		return pkgMap.get(clz.getPackage().getName());
	}

	public static void info(String message, Object... obj)
	{
		getLogger(Reflection.getCallerClass()).info(message, obj);
	}

	public static void trace(String message, Object... obj)
	{
		getLogger(Reflection.getCallerClass()).trace(message, obj);
	}

	public static void fatal(String message, Object... obj)
	{
		getLogger(Reflection.getCallerClass()).fatal(message, obj);
	}

	public static void error(String message, Object... obj)
	{
		getLogger(Reflection.getCallerClass()).error(message, obj);
	}

	public static void warn(String message, Object... obj)
	{
		getLogger(Reflection.getCallerClass()).warn(message, obj);
	}
}
