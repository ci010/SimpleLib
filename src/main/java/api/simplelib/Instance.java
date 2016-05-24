package api.simplelib;

import api.simplelib.utils.TypeUtils;
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
				Instance annotation = field.getAnnotation(Instance.class);
				if (annotation != null)
				{
					int modifiers = field.getModifiers();
					if (!Modifier.isStatic(modifiers))
					{
						HelperMod.LOG.fatal("The field annotated by Instance should be static! cannot grab the instance.");
						return Optional.absent();
					}
					if (!field.getType().isAssignableFrom(clz))
					{
						HelperMod.LOG.fatal("Illegal field type! The type {} cannot cast to type {}", clz, field.getType());
						return Optional.absent();
					}
					T o = null;
					if (Modifier.isPrivate(modifiers))
						o = ReflectionHelper.getPrivateValue(clz, null, field.getName());
					else
						try
						{
							o = TypeUtils.cast(field.get(null));
						}
						catch (IllegalAccessException e)
						{
							e.printStackTrace();
						}
					if (o == null)
						if (!annotation.weak())
							try
							{
								o = clz.newInstance();
							}
							catch (InstantiationException e)
							{
								e.printStackTrace();
							}
							catch (IllegalAccessException e)
							{
								e.printStackTrace();
							}
					return Optional.fromNullable(o);
				}
			}
			return Optional.absent();
		}
	}
}
