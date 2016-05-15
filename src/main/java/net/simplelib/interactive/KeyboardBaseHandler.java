package net.simplelib.interactive;

import test.interactive.Context;
import test.interactive.Interactive;
import test.interactive.base.wrapper.BaseHandler;
import test.interactive.base.wrapper.KeyBoardBaseWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.simplelib.common.registry.KeyBindingHandler;
import net.simplelib.common.registry.abstracts.KeyPair;

/**
 * @author ci010
 */
//@ModInteractiveBaseWrapper(KeyBoardBaseWrapper.class)
public class KeyboardBaseHandler implements BaseHandler
{
	@Override
	public void setup(final Interactive interactive, InteractiveMetadata metadata)
	{
		final Interactive.Base base = interactive.getBase();
		if (base instanceof KeyBoardBaseWrapper)
		{
			KeyBoardBaseWrapper key = (KeyBoardBaseWrapper) base;
			KeyBindingHandler.add(new KeyPair(key.get().id(), key.get().keycode())
			{
				@Override
				public void onKeyPressed()
				{
					final EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
					Context context = new Context()
					{
						@Override
						public World getWorld()
						{
							return Minecraft.getMinecraft().theWorld;
						}

						@Override
						public BlockPos getPos()
						{
							return player.getPosition();
						}

						@Override
						public String getId()
						{
							return "keyboard";
						}

					};
					interactive.getAction(context).interactWith(player, context);
				}
			});
		}
	}

}
