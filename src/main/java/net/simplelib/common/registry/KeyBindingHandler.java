package net.simplelib.common.registry;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.simplelib.common.registry.abstracts.KeyPair;
import api.simplelib.registry.ModHandler;

import java.util.List;

/**
 * @author ci010
 */
@ModHandler
public class KeyBindingHandler
{
	private static int size;
	private static List<KeyPair> pairs;

	public static void add(KeyPair pair)
	{
		if (pairs == null)
			pairs = Lists.newArrayList();
		pairs.add(pair);
	}

	public static void buildList()
	{
		pairs = ImmutableList.copyOf(pairs);
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
