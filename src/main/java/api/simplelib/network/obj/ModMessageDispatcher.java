package api.simplelib.network.obj;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Used to inject a {@link MessageDispatcher} to this field.
 *
 * @author ci010
 * @see MessageDispatcher
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ModMessageDispatcher
{
	Class<? extends MessageType> value();
}
