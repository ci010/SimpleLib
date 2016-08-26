package api.simplelib.registry;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author ci010
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ModWorldGenerator
{
	/**
	 * @return The weight of the generator. Bigger value make it generate later.
	 */
	int weight();
}
