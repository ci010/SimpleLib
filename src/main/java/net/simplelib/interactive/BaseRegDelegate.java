package net.simplelib.interactive;

import api.simplelib.interactive.Interactive;
import api.simplelib.interactive.base.wrapper.BaseHandler;
import api.simplelib.interactive.base.wrapper.ModInteractiveBaseWrapper;
import api.simplelib.registry.ASMRegistryDelegate;
import api.simplelib.utils.GenericUtil;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.simplelib.common.registry.annotation.type.ASMDelegate;

/**
 * @author ci010
 */
//@ASMDelegate
public class BaseRegDelegate extends ASMRegistryDelegate<ModInteractiveBaseWrapper>
{
	@Mod.EventHandler
	public void pre(FMLPreInitializationEvent event)
	{
		Class<? extends BaseHandler> handlerClz = GenericUtil.cast(this.getAnnotatedClass());
		try
		{
			InteractiveMetadata.registerBase(GenericUtil.<Interactive.Base>cast(this.getAnnotation().value()),
					handlerClz.newInstance());
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
