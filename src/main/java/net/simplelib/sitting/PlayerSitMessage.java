package net.simplelib.sitting;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import api.simplelib.network.ModMessage;
import net.simplelib.network.AbstractServerMessageHandler;
import net.simplelib.network.NBTMessage;

/**
 * @author CI010
 */

@ModMessage(PlayerSitMessage.Handler.class)
public class PlayerSitMessage extends NBTMessage
{
	public PlayerSitMessage()
	{}

	public PlayerSitMessage(float offset, BlockPos pos, Block block)
	{
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setInteger("x", pos.getX());
		nbt.setInteger("y", pos.getY());
		nbt.setInteger("z", pos.getZ());
		nbt.setFloat("offset", offset);
		nbt.setInteger("block", Block.getIdFromBlock(block));
		this.data = nbt;
	}


	public static class Handler extends AbstractServerMessageHandler<PlayerSitMessage>
	{
		@Override
		public IMessage handleServerMessage(EntityPlayer player, PlayerSitMessage message, MessageContext ctx)
		{
			System.out.println("handle sit message");
			int x = message.data.getInteger("x"), y = message.data.getInteger("y"), z = message.data.getInteger("z");
			SitHandler.sitOnBlock(player.worldObj, new BlockPos(x, y, z), player, Block.getBlockById(message.data
					.getInteger("block")));
			return null;
		}
	}
}
