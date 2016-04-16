package api.simplelib.interactive.base.wrapper;

import api.simplelib.interactive.Interactive;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author ci010
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ModInteractiveBaseWrapper
{
	Class<? extends  Interactive.Base> value();
}
