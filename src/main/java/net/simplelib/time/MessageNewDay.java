package net.simplelib.time;

import api.simplelib.network.AbstractClientMessage;
import api.simplelib.network.MessageCoder;
import api.simplelib.network.ModMessage;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/**
 * @author ci010
 */
@ModMessage
public class MessageNewDay extends AbstractClientMessage<Void>
{
	public MessageNewDay()
	{
		super(MessageCoder.EMPTY);
	}

	@Override
	public IMessage handleClientMessage(EntityPlayer player, Void data, MessageContext ctx)
	{
		Hook.provider.getController().newDay();
		return null;
	}
}
