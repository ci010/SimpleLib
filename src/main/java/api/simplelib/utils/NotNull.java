package api.simplelib.utils;

import java.lang.annotation.*;

/**
 * @author ci010
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.CLASS)
@Inherited
public @interface NotNull
{
}
