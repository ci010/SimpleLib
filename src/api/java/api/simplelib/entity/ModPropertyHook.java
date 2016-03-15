package api.simplelib.entity;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation register the {@link EntityPropertyHook}.
 *
 * @author ci010
 * @see EntityPropertyHook
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value =
		{ElementType.TYPE})
public @interface ModPropertyHook
{
}
