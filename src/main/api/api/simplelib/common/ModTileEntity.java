package api.simplelib.common;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;

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
public @interface ModTileEntity
{
	/**
	 * @return the id of the tileEntity
	 */
	String value() default "";

	@Retention(RetentionPolicy.RUNTIME)
	@Target(value =
			{ElementType.TYPE})
	@interface Render
	{
		Class<? extends TileEntitySpecialRenderer> value();
	}
}
