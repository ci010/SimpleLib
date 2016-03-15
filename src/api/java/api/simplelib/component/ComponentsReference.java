package api.simplelib.component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>The classes annotated by this annotation will be registered as the classes containing:
 * <ul>
 * <li>{@link net.minecraft.block.Block}
 * <li>{@link net.minecraft.item.Item}
 * <li>{@link ComponentStruct}
 * </ul>
 * which will be register into Minecraft.
 * <p/>
 * <p>This should looks like the {@link net.minecraft.init.Blocks} and {@link net.minecraft.init.Items}</p>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value =
		{ElementType.TYPE})
public @interface ComponentsReference
{
}
