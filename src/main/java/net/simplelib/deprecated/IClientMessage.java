package net.simplelib.deprecated;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/**
 * @author ci010
 */
@Deprecated
public interface IClientMessage<Self extends IClientMessage<Self>> extends IMessage
{
	IMessage onClientMessage(EntityPlayer player, Self message, MessageContext ctx);
}
