package net.simplelib.registry.delegate;

import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.simplelib.RegistryHelper;
import net.simplelib.registry.abstracts.ASMRegistryDelegate;
import net.simplelib.registry.annotation.type.ASMDelegate;
import net.simplelib.registry.annotation.type.Message;
import net.simplelib.network.AbstractMessageHandler;
import net.simplelib.common.utils.GenericUtil;

/**
 * @author ci010
 */
@ASMDelegate
public class PacketRegistryDelegate extends ASMRegistryDelegate<Message>
{
	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		Class<IMessage> msg = GenericUtil.cast(this.getAnnotatedClass());
		Class<AbstractMessageHandler<IMessage>> handler = GenericUtil.cast(this.getAnnotation().value());
		RegistryHelper.INSTANCE.registerMessage(handler, msg);
	}
}
