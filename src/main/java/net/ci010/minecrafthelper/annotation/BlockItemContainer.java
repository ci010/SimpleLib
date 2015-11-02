package net.ci010.minecrafthelper.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The classes annotated by this annotation will be registered as the classes containing the
 * {@link net.minecraft.block.Block}/{@link net.minecraft.item.Item}/{@link net.ci010.minecrafthelper.abstracts.BlockItemStruct}
 * which will be register into Minecraft
 * <p>It's same for you to use {@link net.ci010.minecrafthelper.RegistryHelper#register(Class[])}</p>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value =
		{ElementType.TYPE})
public @interface BlockItemContainer
{

}
