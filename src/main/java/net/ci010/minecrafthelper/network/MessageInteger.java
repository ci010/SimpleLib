package net.ci010.minecrafthelper.network;

import io.netty.buffer.ByteBuf;
import net.ci010.minecrafthelper.annotation.type.Message;
import net.ci010.minecrafthelper.interactive.ContainerWrap;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/**
 * @author ci010
 */
@Message(MessageInteger.Handler.class)
public class MessageInteger implements IMessage
{
	public MessageInteger()
	{}

	private int data, id;

	public MessageInteger(int data)
	{
		this.data = data;
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		data = buf.readInt();
		id = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeInt(data);
		buf.writeInt(id);
	}

	public static class Handler extends AbstractClientMessageHandler<MessageInteger>
	{
		@Override
		public IMessage handleClientMessage(EntityPlayer player, MessageInteger message, MessageContext ctx)
		{
			if (player.openContainer instanceof ContainerWrap)
			{
				ContainerWrap containerWrap = (ContainerWrap) player.openContainer;
				containerWrap.getVar(message.id).setData(message.data);
			}
			return null;
		}
	}
}
