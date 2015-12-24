package net.simplelib.annotation.type;

import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.simplelib.network.AbstractMessageHandler;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author ci010
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value =
		{ElementType.TYPE})
public @interface Message
{
	Class<? extends AbstractMessageHandler<? extends IMessage>> value();
}
