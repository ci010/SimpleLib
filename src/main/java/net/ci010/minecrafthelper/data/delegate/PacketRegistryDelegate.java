package net.ci010.minecrafthelper.data.delegate;

import net.ci010.minecrafthelper.ModNetwork;
import net.ci010.minecrafthelper.RegistryHelper;
import net.ci010.minecrafthelper.abstracts.RegistryDelegate;
import net.ci010.minecrafthelper.annotation.type.ASMDelegate;
import net.ci010.minecrafthelper.annotation.type.Message;
import net.ci010.minecrafthelper.network.AbstractMessageHandler;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

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
