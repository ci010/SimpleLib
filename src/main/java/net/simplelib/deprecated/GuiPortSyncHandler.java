package net.simplelib.deprecated;

import com.google.common.collect.Lists;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.simplelib.HelperMod;
import api.simplelib.VarSync;
import api.simplelib.container.ModGuiHandler;

import java.util.List;

/**
 * @author ci010
 */
@ModGuiHandler
@Deprecated
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
//			ModNetwork.instance().sendTo(new OpenGuiMessage(id, winId, x, y, z), (EntityPlayerMP) player);
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

//	@ModMessage
//	public static class OpenGuiMessage extends NBTCoder implements IServerMessage<OpenGuiMessage>, IClientMessage<OpenGuiMessage>
//	{
//		public OpenGuiMessage() {}
//
//		OpenGuiMessage(int registerId, int winId, int x, int y, int z)
//		{
//			NBTTagCompound tag = new NBTTagCompound();
//			tag.setInteger("registerId", registerId);
//			tag.setInteger("winId", winId);
//			tag.setInteger("x", x);
//			tag.setInteger("y", y);
//			tag.setInteger("z", z);
//		}
//
//		@Override
//		public IMessage onClientMessage(EntityPlayer player, OpenGuiMessage message, MessageContext ctx)
//		{
//			int registerId = message.data.getInteger("registerId");
//			int winId = message.data.getInteger("winId");
//			int x = message.data.getInteger("x"), y = message.data.getInteger("y"), z = message.data.getInteger("z");
//			IGuiPortHandler port = GuiPortSyncHandler.registered.get(registerId);
//			List<VarSync> temp = Lists.newArrayList();
//			port.getPort(player, x, y, z).provideSyncReference(temp);
//			client = new SyncPortalClient(winId, temp);
//			if (!(port instanceof Container))
//			{
//				GuiScreen gui = port.getGui(player, x, y, z);
//				FMLCommonHandler.instance().showGuiScreen(gui);
//			}
//			return message;
//		}
//
//		@Override
//		public IMessage onServerMessage(EntityPlayer player, OpenGuiMessage message, MessageContext ctx)
//		{
//			SyncPortalServer server = (SyncPortalServer) current.get(message.data.getInteger("winId"));
//			for (int i = 0; i < server.data.size(); ++i)
//				ModNetwork.instance().sendTo(new NBTWindowsMessage(server.id, i, server.data.get(i).get()),
//						(EntityPlayerMP) player);
//			return null;
//		}
//	}
	//TODO want to make a sync system for pure GuiScreen without any slot/itemStack information
}
