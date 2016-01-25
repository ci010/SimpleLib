package net.simplelib.common.registry;

import com.google.common.collect.ImmutableList;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.simplelib.common.registry.annotation.type.ModHandler;

import java.util.Collection;

/**
 * @author ci010
 */
@ModHandler
public class KeyBindingHandler
{
	private static int size;
	private static ImmutableList<KeyPair> pairs;

	public static void buildList(Collection<KeyPair> in)
	{
		pairs = ImmutableList.copyOf(in);
		size = pairs.size();
	}

	@SubscribeEvent
	public void onKeyDown(InputEvent.KeyInputEvent event)
	{
		KeyPair pair;
		for (int i = 0; i < size; ++i)
			if ((pair = pairs.get(i)).getKeyBinding().isPressed())
				pair.onKeyPressed();
	}
}
