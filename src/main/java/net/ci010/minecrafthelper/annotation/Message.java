package net.ci010.minecrafthelper.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import net.ci010.minecrafthelper.network.AbstractMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

@Retention(RetentionPolicy.RUNTIME)
@Target(value =
{ ElementType.TYPE })
public @interface Message
{
	Class<? extends AbstractMessageHandler<? extends IMessage>>value();
}
