package net.simplelib.deprecated;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/**
 * @author ci010
 */
public interface IServerMessage<Self extends IServerMessage<Self>> extends IMessage
{
	IMessage onServerMessage(EntityPlayer player, Self message, MessageContext ctx);
}
