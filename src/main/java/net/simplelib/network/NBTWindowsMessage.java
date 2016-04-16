package net.simplelib.network;

import api.simplelib.container.ContainerCommon;
import api.simplelib.network.AbstractClientMessage;
import api.simplelib.network.ModMessage;
import api.simplelib.network.NBTCoder;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import api.simplelib.utils.ITagSerializable;

/**
 * @author ci010
 */
@ModMessage
public class NBTWindowsMessage extends AbstractClientMessage<NBTTagCompound>
{
	public NBTWindowsMessage()
	{super(new NBTCoder());}

	public NBTWindowsMessage(int windowId, int id, ITagSerializable seril)
	{
		super(new NBTCoder());
		NBTTagCompound tag = new NBTTagCompound();
		NBTTagCompound data = new NBTTagCompound();
		seril.writeToNBT(data);
		tag.setTag("data", data);
		tag.setInteger("id", id);
		tag.setInteger("windowId", windowId);
		this.delegate.set(tag);
	}

	@Override
	public IMessage handleClientMessage(EntityPlayer player, NBTTagCompound data, MessageContext ctx)
	{
		if (player.openContainer != null && player.openContainer.windowId == data.getInteger("windowId") &&
				player.openContainer instanceof ContainerCommon)
			((ContainerCommon) player.openContainer).updateSync(data.getInteger("id"), data.getCompoundTag("data"));
		return null;
	}
}
