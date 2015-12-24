package net.simplelib.annotation.type;

import net.simplelib.RegistryHelper;
import net.simplelib.abstracts.BlockItemStruct;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The classes annotated by this annotation will be registered as the classes containing the
 * {@link net.minecraft.block.Block}/{@link net.minecraft.item.Item}/{@link BlockItemStruct}
 * which will be register into Minecraft
 * <p>It's same for you to use {@link RegistryHelper#register(Class[])}</p>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value =
		{ElementType.TYPE})
public @interface BlockItemContainer
{

}
