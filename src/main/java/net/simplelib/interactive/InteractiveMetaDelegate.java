package net.simplelib.interactive;

import api.simplelib.interactive.meta.InteractiveProperty;
import api.simplelib.interactive.meta.ModInteractiveMeta;
import api.simplelib.registry.ASMRegistryDelegate;
import api.simplelib.utils.GenericUtil;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.simplelib.common.registry.annotation.type.ASMDelegate;

/**
 * @author ci010
 */
@ASMDelegate
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
