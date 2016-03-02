package net.simplelib.interactive;

import api.simplelib.interactive.Interactive;
import api.simplelib.interactive.base.BaseHandler;
import api.simplelib.interactive.base.ModInteractiveBase;
import api.simplelib.registry.ASMRegistryDelegate;
import api.simplelib.utils.GenericUtil;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.simplelib.common.registry.annotation.type.ASMDelegate;

/**
 * @author ci010
 */
@ASMDelegate
public class BaseRegDelegate extends ASMRegistryDelegate<ModInteractiveBase>
{
	@Mod.EventHandler
	public void pre(FMLPreInitializationEvent event)
	{
		Class<? extends BaseHandler> handlerClz = this.getAnnotation().value();
		try
		{
			InteractiveMetadata.registerBase(GenericUtil.<Interactive.Base>cast(this.getAnnotatedClass()),
					GenericUtil.<BaseHandler>cast(handlerClz.newInstance()));
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
