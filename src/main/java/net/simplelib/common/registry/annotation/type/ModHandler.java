package net.simplelib.common.registry.annotation.type;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author CI010
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value =
		{ElementType.TYPE})
public @interface ModHandler
{
	Type[] value() default {Type.FML, Type.Forge};

	enum Type
	{
		FML, Forge, Terrain, OreGen
	}
}
