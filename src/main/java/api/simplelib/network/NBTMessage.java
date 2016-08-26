package api.simplelib.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/**
 * @author ci010
 */
public abstract class NBTMessage implements IMessage
{
	private NBTTagCompound tag;

	public NBTMessage(NBTTagCompound tag)
	{
		this.tag = tag;
	}

	public NBTTagCompound getTag()
	{
		return new NBTTagCompound();
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		ByteBufUtils.writeTag(buf, tag);
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		tag = ByteBufUtils.readTag(buf);
	}

	public static abstract class ServerHandler extends AbstractServerMessageHandler<NBTMessage> {}

	public static abstract class ClientHandler extends AbstractClientMessageHandler<NBTMessage> {}

	public static abstract class BiHandler extends AbstractBiMessageHandler<NBTMessage> {}
}
