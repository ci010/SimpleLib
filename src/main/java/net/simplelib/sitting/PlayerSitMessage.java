package net.simplelib.sitting;

import api.simplelib.network.AbstractServerMessageHandler;
import api.simplelib.network.ModMessageHandler;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/**
 * @author CI010
 */

//@ModMessageHandler
public class PlayerSitMessage //extends AbstractServerMessageHandler<NBTTagCompound>
{
//	public PlayerSitMessage()
//	{super(new NBTCoder());}
//
//
//	public PlayerSitMessage(float offset, BlockPos pos, Block block)
//	{
//		super(new NBTCoder());
//		NBTTagCompound nbt = new NBTTagCompound();
//		nbt.setInteger("x", pos.getX());
//		nbt.setInteger("y", pos.getY());
//		nbt.setInteger("z", pos.getZ());
//		nbt.setFloat("translate", offset);
//		nbt.setInteger("block", Block.getIdFromBlock(block));
//		this.delegate.set(nbt);
//	}
//
//	@Override
//	public IMessage handleServerMessage(EntityPlayer player, NBTTagCompound data, MessageContext ctx)
//	{
//		System.out.println("inject sit message");
//		int x = data.getInteger("x"), y = data.getInteger("y"), z = data.getInteger("z");
//		SitHandler.sitOnBlock(player.worldObj, new BlockPos(x, y, z), player, Block.getBlockById(data
//				.getInteger("block")));
//		return null;
//	}
}
