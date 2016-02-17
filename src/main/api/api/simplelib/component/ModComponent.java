package api.simplelib.component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation indicates that the class is a {@link net.minecraft.block.Block} or {@link net.minecraft.item.Item}.
 * and this will be registered into Minecraft when game start.
 *
 * @author ci010
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value =
		{ElementType.TYPE})
public @interface ModComponent
{
	/**
	 * @return The name/id of the block. In default, it will be the simple name of the annotated class.
	 */
	String name() default "";
}
