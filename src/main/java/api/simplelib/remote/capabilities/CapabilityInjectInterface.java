package api.simplelib.remote.capabilities;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The interface annotated by this will inject a capability to the class.
 *
 * @author ci010
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface CapabilityInjectInterface
{
	/**
	 * @return The type of capability this class will inject.
	 */
	Class<?> value();
}
