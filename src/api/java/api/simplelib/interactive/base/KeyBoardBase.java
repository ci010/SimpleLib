package api.simplelib.interactive.base;

import api.simplelib.interactive.Interactive;
import net.simplelib.interactive.KeyboardBaseHandler;

/**
 * @author ci010
 */
@ModInteractiveBase(KeyboardBaseHandler.class)
public interface KeyBoardBase extends Interactive.Base<KeyBoardBase.KeyInfo>
{
	interface KeyInfo
	{
		int keycode();

		String id();
	}
}
