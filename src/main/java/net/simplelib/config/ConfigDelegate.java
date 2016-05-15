package net.simplelib.config;

import api.simplelib.registry.ModConfig;
import api.simplelib.registry.ASMRegistryDelegate;
import com.google.common.base.Optional;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.io.File;

/**
 * @author ci010
 */
//@ASMDelegate
public class ConfigDelegate extends ASMRegistryDelegate<ModConfig>
{
	@SubscribeEvent
	public void init(FMLPreInitializationEvent event)
	{
		File dic = event.getModConfigurationDirectory();
		Configuration configuration = new Configuration(dic, this.getModid());
		Optional<Object> object = this.getObject();
		if (object.isPresent())
		{
			Object real = object.get();
			Class<?> type = real.getClass();

		}
	}
}
