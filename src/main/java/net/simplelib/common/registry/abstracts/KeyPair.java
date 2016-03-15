package net.simplelib.common.registry.abstracts;

import api.simplelib.Local;
import net.minecraft.client.settings.KeyBinding;

/**
 * Reference {@link org.lwjgl.input.Keyboard}
 *
 * @author ci010
 */
public abstract class KeyPair
{
	private KeyBinding mcImpl;

	public KeyPair(String id, int keycode)
	{
		this.mcImpl = new KeyBinding(Local.trans("key." + id + ".description", id), keycode,
				Local.trans("key." + id + ".category", id));
	}

	public final KeyBinding getKeyBinding()
	{
		return mcImpl;
	}

	public abstract void onKeyPressed();
}
