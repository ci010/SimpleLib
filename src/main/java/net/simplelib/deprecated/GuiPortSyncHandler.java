package net.simplelib.deprecated;

import com.google.common.collect.Lists;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.simplelib.HelperMod;
import net.simplelib.common.VarSync;
import net.simplelib.common.registry.annotation.type.Message;
import net.simplelib.common.registry.annotation.type.ModGuiHandler;
import net.simplelib.network.AbstractBiMessageHandler;
import net.simplelib.network.ModNetwork;
import net.simplelib.network.NBTMessage;
import net.simplelib.network.NBTWindowsMessage;

import java.util.List;

/**
 * @author ci010
 */
@ModGuiHandler
public class GuiPortSyncHandler implements IGuiHandler
{
	private static List<SyncPortal> current = Lists.newArrayList();
	static Side side = FMLCommonHandler.instance().getSide();
	static List<IGuiPortHandler> registered;
	@SideOnly(Side.CLIENT)
	static SyncPortal client;

	//
	public static void register(IGuiPortHandler port)
	{
		if (registered == null)
			registered = Lists.newArrayList();
		registered.add(port);
	}

	public static void openGui(int id, EntityPlayer player, int x, int y, int z)
	{
		if (side == Side.SERVER)
		{
			List<VarSync> temp = Lists.newArrayList();
			//TODO consider if I need to store this reference
			IGuiPortHandler handler = registered.get(id);
			handler.getPort(player, x, y, z).provideSyncReference(temp);
			int winId = current.size();
			SyncPortalServer server = new SyncPortalServer(winId, temp);
			current.add(server);
			ModNetwork.instance().sendTo(new OpenGuiMessage(id, winId, x, y, z), (EntityPlayerMP) player);
			if (handler instanceof Container)
				player.openGui(HelperMod.instance, id, player.worldObj, x, y, z);
		}
	}

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		return registered.get(ID).getPort(player, x, y, z);
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		return registered.get(ID).getGui(player, x, y, z);
	}

	@Message(OpenGuiMsgHandler.class)
	public static class OpenGuiMessage extends NBTMessage implements IMessage
	{
		public OpenGuiMessage() {}

		OpenGuiMessage(int registerId, int winId, int x, int y, int z)
		{
			NBTTagCompound tag = new NBTTagCompound();
			tag.setInteger("registerId", registerId);
			tag.setInteger("winId", winId);
			tag.setInteger("x", x);
			tag.setInteger("y", y);
			tag.setInteger("z", z);
		}
	}

	public static class OpenGuiMsgHandler extends AbstractBiMessageHandler<OpenGuiMessage>
	{
		@Override
		public IMessage handleClientMessage(EntityPlayer player, OpenGuiMessage message, MessageContext ctx)
		{
			int registerId = message.data.getInteger("registerId");
			int winId = message.data.getInteger("winId");
			int x = message.data.getInteger("x"), y = message.data.getInteger("y"), z = message.data.getInteger("z");
			IGuiPortHandler port = GuiPortSyncHandler.registered.get(registerId);
			List<VarSync> temp = Lists.newArrayList();
			port.getPort(player, x, y, z).provideSyncReference(temp);
			client = new SyncPortalClient(winId, temp);
			if (!(port instanceof Container))
			{
				GuiScreen gui = port.getGui(player, x, y, z);
				FMLCommonHandler.instance().showGuiScreen(gui);
			}
			return message;
		}

		@Override
		public IMessage handleServerMessage(EntityPlayer player, OpenGuiMessage message, MessageContext ctx)
		{
			SyncPortalServer server = (SyncPortalServer) current.get(message.data.getInteger("winId"));
			for (int i = 0; i < server.data.size(); ++i)
				ModNetwork.instance().sendTo(new NBTWindowsMessage(server.id, i, server.data.get(i).get()),
						(EntityPlayerMP) player);
			return null;
		}
	}
	//TODO want to make a sync system for pure GuiScreen without any slot/itemStack information
}
