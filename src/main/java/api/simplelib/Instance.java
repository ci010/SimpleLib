package api.simplelib;

import api.simplelib.utils.GenericUtil;
import com.google.common.base.Optional;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.simplelib.HelperMod;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * @author ci010
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value =
		{ElementType.FIELD})
public @interface Instance
{
	boolean weak() default false;

	class Utils
	{
		public static <T> Optional<T> grab(Class<T> clz)
		{
			for (Field field : clz.getDeclaredFields())
			{
				if (field.isAnnotationPresent(Instance.class))
				{
					int modifiers = field.getModifiers();
					if (!Modifier.isStatic(modifiers))
					{
						HelperMod.LOG.fatal("The field annotated by Instance should be static! cannot grab the instance.");
						return Optional.absent();
					}
					if (Modifier.isPrivate(modifiers))
						return ReflectionHelper.getPrivateValue(clz, null, field.getName());
					else
						try
						{
							return Optional.fromNullable(GenericUtil.<T>cast(field.get(null)));
						}
						catch (IllegalAccessException e)
						{
							e.printStackTrace();
						}
				}
			}
			return Optional.absent();
		}
	}
}
