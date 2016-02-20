package net.simplelib.network;

import api.simplelib.network.IClientMessage;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.simplelib.common.nbt.ITagSerial;
import api.simplelib.network.ModMessage;
import net.simplelib.interactive.ContainerCommon;

/**
 * @author ci010
 */
@ModMessage
public class NBTWindowsMessage extends NBTMessage implements IClientMessage<NBTWindowsMessage>
{
	public NBTWindowsMessage()
	{}

	public NBTWindowsMessage(int windowId, int id, ITagSerial seril)
	{
		NBTTagCompound tag = new NBTTagCompound();
		NBTTagCompound data = new NBTTagCompound();
		seril.writeToNBT(data);
		tag.setTag("data", data);
		tag.setInteger("id", id);
		tag.setInteger("windowId", windowId);
		this.data = tag;
	}

	@Override
	public IMessage onClientMessage(EntityPlayer player, NBTWindowsMessage message, MessageContext ctx)
	{
		if (player.openContainer != null && player.openContainer.windowId == message.data.getInteger("windowId") && player.openContainer instanceof
				ContainerCommon)
			((ContainerCommon) player.openContainer).updateSync(message.data.getInteger("id"), message.data
					.getCompoundTag("data"));
		return null;
	}
}
