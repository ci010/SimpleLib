package net.ci010.minecrafthelper.annotation.field;

import net.ci010.minecrafthelper.network.AbstractMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author CI010
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value =
		{ElementType.FIELD})
public @interface OreDic
{
	String value() default "";

}
