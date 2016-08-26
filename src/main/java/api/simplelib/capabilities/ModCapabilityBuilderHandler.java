package api.simplelib.capabilities;

import java.lang.annotation.*;

/**
 * This annotation will register the handler class {@link CapabilityBuilderHandler}.
 *
 * @author ci010
 * @see CapabilityBuilderHandler
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ModCapabilityBuilderHandler {}
