package api.simplelib.network;


import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The class annotated by this annotation will be registered as a message which can be sent to server/client.
 *
 * @author ci010
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value =
		{ElementType.TYPE})
public @interface ModMessageHandler
{
	Class<? extends IMessage> value();
}
