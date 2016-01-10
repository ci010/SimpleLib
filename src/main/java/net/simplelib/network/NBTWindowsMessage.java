package net.simplelib.network;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.simplelib.common.NBTSeril;
import net.simplelib.common.registry.annotation.type.Message;
import net.simplelib.interactive.ContainerCommon;

/**
 * @author ci010
 */
@Message(NBTWindowsMessage.NBTWindowsMessageHandler.class)
public class NBTWindowsMessage extends NBTMessage
{
	public NBTWindowsMessage()
	{}

	public NBTWindowsMessage(int windowId, int id, NBTSeril seril)
	{
		NBTTagCompound tag = new NBTTagCompound();
		NBTTagCompound data = new NBTTagCompound();
		seril.writeToNBT(data);
		tag.setTag("data", data);
		tag.setInteger("id", id);
		tag.setInteger("windowId", windowId);
		this.data = tag;
	}


	public static class NBTWindowsMessageHandler extends AbstractClientMessageHandler<NBTWindowsMessage>
	{
		@Override
		public IMessage handleClientMessage(EntityPlayer player, NBTWindowsMessage message, MessageContext ctx)
		{
			if (player.openContainer != null && player.openContainer.windowId == message.data.getInteger("windowId") && player.openContainer instanceof
					ContainerCommon)
				((ContainerCommon) player.openContainer).updateSync(message.data.getInteger("id"), message.data
						.getCompoundTag("data"));
			return null;
		}
	}
}
