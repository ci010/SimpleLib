package net.simplelib.common.registry.annotation.type;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.simplelib.network.AbstractMessageHandler;
import net.simplelib.network.ModNetwork;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The class annotated by this annotation will be registered as a message which can be sent to server/client.
 * <p>The message will be registered as {@link ModNetwork#registerMessage(Class, Class)}</p>
 * <p>About the usage of the message, see
 * {@link ModNetwork#sendTo(IMessage)} and {@link ModNetwork#sendTo(IMessage, EntityPlayerMP)}.</p>
 *
 * @author ci010
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value =
		{ElementType.TYPE})
public @interface Message
{
	/**
	 * @return The handler of the class. This is necessary to decide the usage of a message.
	 */
	Class<? extends AbstractMessageHandler<? extends IMessage>> value();
}
