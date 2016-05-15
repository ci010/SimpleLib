package net.simplelib.interactive;

import test.interactive.meta.InteractiveProperty;
import test.interactive.meta.ModInteractiveMeta;
import api.simplelib.registry.ASMRegistryDelegate;
import api.simplelib.utils.GenericUtil;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import api.simplelib.LoadingDelegate;

/**
 * @author ci010
 */
@LoadingDelegate
public class InteractiveMetaDelegate extends ASMRegistryDelegate<ModInteractiveMeta>
{
	@Mod.EventHandler
	public void pre(FMLPreInitializationEvent event)
	{
		Class<?> annotatedClass = this.getAnnotatedClass();
		if (InteractiveProperty.class.isAssignableFrom(annotatedClass))
		{
			Class<? extends InteractiveProperty> clz;
			InteractiveMetadata.registerMeta(clz = GenericUtil.cast(annotatedClass));
		}
		else
		{
			//TODO log
		}
	}
}
