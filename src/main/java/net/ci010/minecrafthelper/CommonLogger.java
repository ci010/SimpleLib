package net.ci010.minecrafthelper;

import com.google.common.collect.Maps;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

/**
 * @author ci010
 */
public class CommonLogger
{
	static Map<String, Logger> pkgMap = Maps.newHashMap(), modidMap = Maps.newHashMap();

	private static Logger getLogger()
	{
		String trace = new Throwable().getStackTrace()[2].toString();
		Logger log = null;
		if (!pkgMap.containsKey(trace))
		{
			String temp = trace.substring(0, trace.lastIndexOf("."));
			temp = temp.substring(0, temp.lastIndexOf("."));
			String pkg = temp.substring(0, temp.lastIndexOf("."));

			if (!pkgMap.containsKey(trace))
				for (ModContainer mod : Loader.instance().getActiveModList())
					if (mod.getOwnedPackages().contains(pkg))
						if (!modidMap.containsKey(mod.getModId()))
						{
							log = LogManager.getLogger(mod.getModId());
							pkgMap.put(trace, log);
							modidMap.put(mod.getModId(), log);
						}
						else
						{
							log = modidMap.get(mod.getModId());
							pkgMap.put(trace, log);
						}
					else
						System.out.println("WTF");
		}
		return log;
	}

	public static void info(String message, Object... obj)
	{
		getLogger().info(message, obj);
	}

	public static void trace(String message, Object... obj)
	{
		getLogger().trace(message, obj);
	}

	public static void fatal(String message, Object... obj)
	{
		getLogger().fatal(message, obj);
	}

	public static void error(String message, Object... obj)
	{
		getLogger().error(message, obj);
	}

	public static void warn(String message, Object... obj)
	{
		getLogger().warn(message, obj);
	}
}
