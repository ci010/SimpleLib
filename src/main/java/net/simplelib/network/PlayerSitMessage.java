package net.simplelib.network;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.simplelib.registry.annotation.type.Message;
import net.simplelib.registry.SitHandler;

/**
 * @author CI010
 */

@Message(PlayerSitMessage.Handler.class)
public class PlayerSitMessage extends NBTMessage
{
	public PlayerSitMessage()
	{}

	public PlayerSitMessage(BlockPos pos, EnumFacing face)
	{
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setInteger("x", pos.getX());
		nbt.setInteger("y", pos.getY());
		nbt.setInteger("z", pos.getZ());
		nbt.setString("face", face.getName2());
		this.data = nbt;
	}


	public static class Handler extends AbstractServerMessageHandler<PlayerSitMessage>
	{
		@Override
		public IMessage handleServerMessage(EntityPlayer player, PlayerSitMessage message, MessageContext ctx)
		{
			int x = message.data.getInteger("x"), y = message.data.getInteger("y"), z = message.data.getInteger("z");
			SitHandler.sitOnBlock(player.worldObj, new BlockPos(x, y, z), player, EnumFacing.byName(message.data
					.getString
							("face")));
			return null;
		}
	}
}
