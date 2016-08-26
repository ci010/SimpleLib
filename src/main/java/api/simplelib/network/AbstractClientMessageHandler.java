package api.simplelib.network;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public abstract class AbstractClientMessageHandler<T extends IMessage> extends AbstractMessageHandler<T>
{
	@Override
	public final IMessage handleServerMessage(EntityPlayer player, T data, MessageContext ctx)
	{
		return null;
	}
}
