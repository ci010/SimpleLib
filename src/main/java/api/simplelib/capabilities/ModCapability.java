package api.simplelib.capabilities;

import api.simplelib.registry.FreeConstruct;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotations is used to register {@link ICapability}.
 *
 * @author ci010
 * @see ICapability
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@FreeConstruct
public @interface ModCapability {}
