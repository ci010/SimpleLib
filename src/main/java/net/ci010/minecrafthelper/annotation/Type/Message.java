package net.ci010.minecrafthelper.annotation.type;

import net.ci010.minecrafthelper.network.AbstractMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

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
