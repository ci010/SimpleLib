package api.simplelib.registry.key;

import net.minecraft.client.settings.KeyBinding;

/**
 * The class that handle keyboard's key pressing.
 * <p>Use
 * {@link ModKeyBinding} to register and assign what key pressed will call {@link KeyHandler#onKeyPressed(KeyBinding)}</p>
 *
 * @author ci010
 */
public interface KeyHandler
{
	/**
	 * This method will be called when the key with {@link ModKeyBinding#keyCode()} is pressed.
	 *
	 * @param binding The key binding detail information.
	 */
	void onKeyPressed(KeyBinding binding);
}
