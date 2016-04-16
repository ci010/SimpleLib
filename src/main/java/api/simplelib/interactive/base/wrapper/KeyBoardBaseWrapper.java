package api.simplelib.interactive.base.wrapper;

import api.simplelib.interactive.Interactive;

/**
 * @author ci010
 */
public interface KeyBoardBaseWrapper extends BaseWrapper<KeyBoardBaseWrapper.KeyInfo>
{
	interface KeyInfo
	{
		int keycode();

		String id();
	}
}
