package api.simplelib.component;

import java.lang.annotation.*;

/**
 * This annotation indicates that the class is a specific structure with blocks/items.
 * The blocks/items will be register if the class is annotated by {@link ModComponent}</li>
 *
 * @author ci010
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value =
		{ElementType.TYPE})
@Inherited
public @interface ModComponentStruct
{
}
