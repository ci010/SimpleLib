package net.simplelib.time;

import api.simplelib.network.AbstractClientMessage;
import api.simplelib.network.ModMessage;
import api.simplelib.network.NBTCoder;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/**
 * @author ci010
 */
@ModMessage
public class MessageTimeSync extends AbstractClientMessage<NBTTagCompound>
{
	public MessageTimeSync()
	{super(new NBTCoder());}

	@Override
	public IMessage handleClientMessage(EntityPlayer player, NBTTagCompound data, MessageContext ctx)
	{
		Hook.provider.getController().readFromNBT(data);
		return null;
	}

	public MessageTimeSync(TimeController controller)
	{
		super(new NBTCoder());
		this.delegate.set(new NBTTagCompound());
		controller.writeToNBT(this.delegate.get());
	}
}
