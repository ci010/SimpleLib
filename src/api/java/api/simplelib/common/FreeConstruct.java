package api.simplelib.common;

import java.lang.annotation.*;

/**
 * This annotation indicates that this class/interface should have a public non-parameter constructor.
 *
 * @author ci010
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.SOURCE)
@Inherited
public @interface FreeConstruct
{
}
