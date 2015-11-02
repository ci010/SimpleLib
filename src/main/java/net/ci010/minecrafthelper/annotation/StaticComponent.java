package net.ci010.minecrafthelper.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation works for those items or blocks just want to have one instance.
 * <p>Notice that the constructor needs to have no argument.<p/>
 * <p>To get the instance created by this annotation, see {@link net.ci010.minecrafthelper.ComponentsRepository}</p>
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(value =
		{ElementType.TYPE})
public @interface StaticComponent
{
	String ordDic() default "";
}
