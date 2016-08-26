package api.simplelib.sync.container;

import api.simplelib.network.NBTMessage;
import api.simplelib.sync.CapabilityProvidersSerializer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/**
 * @author ci010
 */
public class OpenGuiMessage extends NBTMessage
{
	public OpenGuiMessage(NBTTagCompound tag)
	{
		super(tag);
	}

	public static class Handler extends NBTMessage.BiHandler
	{
		@Override
		public IMessage handleClientMessage(EntityPlayer player, NBTMessage data, MessageContext ctx)
		{
			NBTTagCompound tag = data.getTag();

			return null;
		}

		@Override
		public IMessage handleServerMessage(EntityPlayer player, NBTMessage data, MessageContext ctx)
		{
			return null;
		}
	}

	public static IMessage fromTileEntity(EntityPlayerMP player, TileEntity tileEntity)
	{
		NBTTagCompound of = CapabilityProvidersSerializer.of(tileEntity);
		NBTTagCompound tag = new NBTTagCompound();
		tag.setTag("location", of);
		OpenGuiMessage msg = new OpenGuiMessage(tag);
		return msg;
	}

	public static IMessage fromEntity(EntityPlayerMP player, Entity entity)
	{
		return null;
	}
}
