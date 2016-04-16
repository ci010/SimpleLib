package test.api.component;

import com.google.common.base.Optional;
import test.api.world.World;

/**
 * The context of update in minecraft world
 *
 * @author ci010
 */
public interface Context
{
	World world();

	State getState();

	Optional<String> contextId();
}
