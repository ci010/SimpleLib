package net.simplelib.common.registry;

import net.minecraft.client.settings.KeyBinding;

/**
 * Reference {@link org.lwjgl.input.Keyboard}
 *
 * @author ci010
 */
public abstract class KeyPair
{
	private KeyBinding mcImpl;

	public KeyPair(String description, int keyCode, String category)
	{
		this.mcImpl = new KeyBinding(description, keyCode, category);
	}

	public final KeyBinding getKeyBinding()
	{
		return mcImpl;
	}

	public abstract void onKeyPressed();
}
