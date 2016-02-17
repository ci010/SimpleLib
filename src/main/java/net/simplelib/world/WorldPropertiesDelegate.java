package net.simplelib.world;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import api.simplelib.registry.ASMRegistryDelegate;
import net.simplelib.common.registry.annotation.type.ASMDelegate;

/**
 * @author ci010
 */
@ASMDelegate
public class WorldPropertiesDelegate extends ASMRegistryDelegate<IWorldProperties>
{
	@Mod.EventHandler
	public void init(FMLInitializationEvent event)
	{
		try
		{
			IExtendedWorldProperties hook = (IExtendedWorldProperties) this.getAnnotatedClass().newInstance();
			WorldPropertiesManager.instance().register(this.getAnnotation().dimension(),
					this.getAnnotation().name().equals("")
							? this.getAnnotatedClass().getSimpleName()
							: this.getAnnotation().name(), hook);
		}
		catch (InstantiationException e)
		{
			e.printStackTrace();
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
	}
}
