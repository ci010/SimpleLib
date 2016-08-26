package api.simplelib.registry;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation indicates that the field is a mod config.
 * <p>The default value of the config will be applied by this field's value.</p>
 * <p>For array, if you want a fix length array, use {@link FixLength}.</p>
 * <p>To limit the range of the config value, use {@link ValidRange}.</p>
 * <p>It supports the {@link api.simplelib.Instance} to allocate instance.</p>
 *
 * @author ci010
 * @see api.simplelib.Instance
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ModConfig
{
	/**
	 * @return The domain of the config. This will decide which config file it use. In default situation, it will
	 * inspect the mod id of this class by package location.
	 */
	String domain() default "";

	/**
	 * @return The category id of this config.
	 */
	String categoryId() default "default";

	/**
	 * @return The id of the config.
	 */
	String id() default "";

	String comment() default "";

	boolean showInGui() default false;

	boolean requireMCRestart() default false;

	boolean requireWorldRestart() default false;

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.FIELD)
	@interface ValidRange
	{
		/**
		 * @return The valid range of the config. If the config is number, it will find the max/min value in this
		 * range to set as maximum value and minimum value.
		 */
		String[] range();
	}

	/**
	 * An OPTIONAL @interface for array. If your target is an array, and you didn't initialized array, allocated max
	 * length to getResource a fix length array.
	 */
	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.FIELD)
	@interface FixLength
	{
		int maxLength();
	}
}
