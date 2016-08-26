package net.simplelib.registry.delegate;

import api.simplelib.Instance;
import api.simplelib.LoadingDelegate;
import api.simplelib.registry.ASMRegistryDelegate;
import api.simplelib.registry.ModWorldGenerator;
import api.simplelib.utils.DebugLogger;
import api.simplelib.utils.TypeUtils;
import com.google.common.base.Optional;
import net.minecraftforge.fml.common.IWorldGenerator;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * @author ci010
 */
@LoadingDelegate
public class WorldGenDelegate extends ASMRegistryDelegate<ModWorldGenerator>
{
	@Mod.EventHandler
	public void init(FMLPreInitializationEvent event)
	{
		Class<?> clz = this.getAnnotatedClass();
		if (IWorldGenerator.class.isAssignableFrom(clz))
		{
			Optional<IWorldGenerator> optional = TypeUtils.cast(Instance.Utils.grabAll(clz));
			IWorldGenerator generator;
			if (optional.isPresent())
				generator = optional.get();
			else
				generator = (IWorldGenerator) TypeUtils.instantiateQuite(clz);
			if (generator != null)
			{
				GameRegistry.registerWorldGenerator(generator, this
						.getAnnotation().weight());
				DebugLogger.info("Register IWorldGenerator: [{}] <- [{}]", clz.getSimpleName(), this.getAnnotation().weight());
			}
			else
				DebugLogger.fatal("Cannot register [{}] as a IWorldGenerator as it does't have a public non-parameter" +
						" constructor!", clz.getName());

		}
		else
			DebugLogger.fatal("Cannot register [{}] as a IWorldGenerator as it does't extends IWorldGenerator!", clz.getName());
	}
}
