package api.simplelib.utils;

import api.simplelib.LoadingDelegate;
import api.simplelib.registry.ASMRegistryDelegate;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.message.MessageFactory;

/**
 * @author ci010
 */
public class DebugLogger
{
	private static Logger logger = LogManager.getLogger();
	private static PackageContextMap<Marker> contextMap = new PackageContextMap<Marker>();

	private static class SimpleMarker implements Marker
	{
		String name;

		public SimpleMarker(String name)
		{
			this.name = name;
		}

		@Override
		public String getName()
		{
			return name;
		}

		@Override
		public Marker getParent() {return null;}

		@Override
		public boolean isInstanceOf(Marker m) {return m instanceof SimpleMarker;}

		@Override
		public boolean isInstanceOf(String name) {return false;}
	}

	@LoadingDelegate
	public static class MarkerDelegate extends ASMRegistryDelegate<MarkerContext>
	{
		@Mod.EventHandler
		public void init(FMLInitializationEvent event)
		{
			contextMap.putContext(this.getAnnotatedClass().getPackage(), new SimpleMarker(this.getAnnotation().value()));
		}
	}

	public static void infoFrom(Object context, String message, Object... obj)
	{
		if (Environment.isDebugEnvironment())
		{
			logger.info(contextMap.get(new Throwable().getStackTrace()[1].getClassName()), message, obj);
		}
	}

	public static void traceFrom(Object context, String message, Object... obj)
	{
		if (Environment.isDebugEnvironment())
			logger.trace(contextMap.getContext(context.getClass()), message, obj);
	}

	public static void fatalInContext(Object context, String message, Object... obj)
	{
		if (Environment.isDebugEnvironment())
			logger.fatal(contextMap.getContext(context.getClass()), message, obj);
	}

	public static void errorInContext(Object context, String message, Object... obj)
	{
		if (Environment.isDebugEnvironment())
			logger.error(contextMap.getContext(context.getClass()), message, obj);
	}

	public static void warnInContext(Object context, String message, Object... obj)
	{
		if (Environment.isDebugEnvironment())
			logger.warn(contextMap.getContext(context.getClass()), message, obj);
	}

	public static void info(String message, Object... obj)
	{
		if (Environment.isDebugEnvironment())
			logger.info(message, obj);
	}

	public static void trace(String message, Object... obj)
	{
		if (Environment.isDebugEnvironment())
			logger.trace(message, obj);
	}

	public static void fatal(String message, Object... obj)
	{
		if (Environment.isDebugEnvironment())
			logger.fatal(message, obj);
	}

	public static void error(String message, Object... obj)
	{
		if (Environment.isDebugEnvironment())
			logger.error(message, obj);
	}

	public static void warn(String message, Object... obj)
	{
		if (Environment.isDebugEnvironment())
			logger.warn(message, obj);
	}

	static
	{
//		LoggerContext context = LogManager.getContext(DebugLogger.class.getClassLoader(), true);
//		org.apache.logging.log4j.core.LoggerContext real = (org.apache.logging.log4j.core.LoggerContext) context;
//		real.getConfiguration()
//		Configuration cfg = real.getConfiguration();
//		System.out.println(real.getConfigLocation());
//		System.out.println(cfg);
//		RollingRandomAccessFileAppender file = (RollingRandomAccessFileAppender) cfg.getAppenders().get("File");
//		for (Map.Entry<String, Appender> stringAppenderEntry : cfg.getAppenders().entrySet())
//		{
//			System.out.println(stringAppenderEntry.getKey()+ " " + stringAppenderEntry.getValue().getClass());
//		}
//		FileAppender.createAppender("test","","","test",null,null.);
//		FileAppender.createAppender("logs/test.log","true")
//		System.out.println(context);
	}
}
