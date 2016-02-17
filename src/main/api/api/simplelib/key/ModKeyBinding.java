package api.simplelib.key;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author ci010
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value =
		{ElementType.TYPE})
public @interface ModKeyBinding
{
	/**
	 * @return The id of the ModKeyBinding. This will be the reference of the description and category.
	 */
	String id();

	/**
	 * @return The key code in integer. Reference {@link org.lwjgl.input.Keyboard}.
	 */
	int keyCode();
}
