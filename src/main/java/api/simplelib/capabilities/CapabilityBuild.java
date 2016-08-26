package api.simplelib.capabilities;

import java.lang.annotation.*;

/**
 * The method annotated by this will provide a
 * {@link net.minecraftforge.common.capabilities.Capability}.
 * <p>
 * The form should be:
 * <p>@CapabilityBuild
 * <p>{@code public void buildSomething(CapabilityBuilder context){...}}</p>
 * <p>The getName of the method is free. Can be anything.
 * <p>The object containing the methods annotated by this will be able to handle by
 * <p>{@link Capabilities#revolve(net.minecraft.tileentity.TileEntity)},
 * <p>{@link Capabilities#revolve(net.minecraft.item.ItemStack)}</p>
 *
 * @see CapabilityBuilder
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Inherited
public @interface CapabilityBuild
{
	/**
	 * @return The weight of the capability. Lighter will run first.
	 */
	byte weight() default 0;
}
