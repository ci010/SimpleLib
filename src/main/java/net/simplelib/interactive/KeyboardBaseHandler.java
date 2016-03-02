package net.simplelib.interactive;

import api.simplelib.interactive.Interactive;
import api.simplelib.interactive.base.BaseHandler;
import api.simplelib.interactive.base.KeyBoardBase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.simplelib.common.registry.KeyBindingHandler;
import net.simplelib.common.registry.abstracts.KeyPair;

/**
 * @author ci010
 */
public class KeyboardBaseHandler implements BaseHandler
{
	@Override
	public void handle(final Interactive interactive)
	{
		final Interactive.Base base = interactive.getBase();
		if (base instanceof KeyBoardBase)
		{
			KeyBoardBase key = (KeyBoardBase) base;
			KeyBindingHandler.add(new KeyPair(key.get().id(), key.get().keycode())
			{
				@Override
				public void onKeyPressed()
				{
					final EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
					interactive.getAction().interactWith(player, player.getPosition());
				}
			});
		}
	}
}
