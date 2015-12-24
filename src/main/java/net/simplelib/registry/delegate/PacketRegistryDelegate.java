package net.simplelib.registry.delegate;

import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.simplelib.RegistryHelper;
import net.simplelib.abstracts.RegistryDelegate;
import net.simplelib.annotation.type.ASMDelegate;
import net.simplelib.annotation.type.Message;
import net.simplelib.network.AbstractMessageHandler;

/**
 * @author ci010
 */
@ASMDelegate
public class PacketRegistryDelegate extends RegistryDelegate<Message>
{
	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		Class<IMessage> msg = (Class<IMessage>) this.getAnnotatedClass();
		Class<AbstractMessageHandler<IMessage>> handler = (Class<AbstractMessageHandler<IMessage>>) this.getAnnotation().value();
		RegistryHelper.INSTANCE.registerMessage(handler, msg);
	}
}
