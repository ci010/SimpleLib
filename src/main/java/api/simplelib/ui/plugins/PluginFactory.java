package api.simplelib.ui.plugins;


import com.google.common.base.Function;
import com.google.common.collect.Maps;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * @author ci010
 */
public class PluginFactory
{
	private static Map<ResourceLocation, Function<Object[], Plugin>> reg = Maps.newHashMap();

	public static void register(ResourceLocation location, Class<? extends Plugin> clz)
	{
		Constructor<?>[] constructors = clz.getConstructors();
		if (constructors == null || constructors.length == 0)
			throw new IllegalArgumentException();
		final Constructor<?> constructor = constructors[0];
		register(location, new Function<Object[], Plugin>()
		{
			@Nullable
			@Override
			public Plugin apply(@Nullable Object[] input)
			{
				try
				{
					return (Plugin) constructor.newInstance(input);
				}
				catch (InstantiationException | IllegalAccessException | InvocationTargetException e)
				{
					e.printStackTrace();
				}
				return null;
			}
		});
	}

	public static void register(ResourceLocation location, Function<Object[], Plugin> construct)
	{
		reg.put(location, construct);
	}

	public static Plugin instantate(ResourceLocation location, Object... args)
	{
		return reg.get(location).apply(args);
	}
}
