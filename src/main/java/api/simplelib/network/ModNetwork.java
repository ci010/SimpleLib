package api.simplelib.network;

import api.simplelib.utils.GenericUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

/**
 * @author coolAlias
 * @author CIJhn
 */
public class ModNetwork
{
	private static ModNetwork instance;
	private byte packetId = 0;
	/**
	 * The SimpleNetworkWrapper instance is used both to register and send
	 * packets. Since I will be adding wrapper methods, this field is private,
	 * but you should update it public if you plan on using it directly.
	 */
	private final SimpleNetworkWrapper dispatcher;

	public static ModNetwork instance()
	{
		if (instance == null) instance = new ModNetwork("net"); return instance;
	}

	private ModNetwork(String modid)
	{
		dispatcher = NetworkRegistry.INSTANCE.newSimpleChannel(modid);
	}


	/**
	 * Registers a message and message handler
	 */
	public final <Message extends IMessage> void registerMessage(IMessageHandler<Message, IMessage> handler)
	{
		Class<Message> messageClass;
		if (handler instanceof AbstractClientMessage)
			dispatcher.registerMessage(handler, messageClass = GenericUtil.cast(handler.getClass()), packetId++, Side.CLIENT);
		else if (handler instanceof AbstractServerMessage)
			dispatcher.registerMessage(handler, messageClass = GenericUtil.cast(handler.getClass()), packetId++, Side.SERVER);
		else if (handler instanceof AbstractBiMessage)
		{
			dispatcher.registerMessage(handler, messageClass = GenericUtil.cast(handler.getClass()), packetId, Side.CLIENT);
			dispatcher.registerMessage(handler, messageClass = GenericUtil.cast(handler.getClass()), packetId++, Side.SERVER);
		}
		else
		{
			throw new IllegalArgumentException("Cannot register " + handler.getClass().getName() +
					". Not Support type ModHandler maybe?");
		}
	}

	/**
	 * Send this message to the specified client of the player. See
	 * {@link SimpleNetworkWrapper#sendTo(IMessage, EntityPlayerMP)}
	 */
	public final void sendTo(IMessage message, EntityPlayerMP player)
	{
		dispatcher.sendTo(message, player);
	}

	/**
	 * Send this message to the server.
	 *
	 * @param message The message will be sent.
	 */
	public final void sendTo(IMessage message)
	{
		this.sendToServer(message);
	}

	/**
	 * Send this message to everyone within a certain range of a point. See
	 * {@link SimpleNetworkWrapper#sendToDimension(IMessage, int)}
	 */
	public final void sendToAllAround(IMessage message, NetworkRegistry.TargetPoint point)
	{
		dispatcher.sendToAllAround(message, point);
	}

	/**
	 * Sends a message to everyone within a certain range of the coordinates in
	 * the same dimension.
	 */
	public final void sendToAllAround(IMessage message, int dimension, double x, double y, double z,
									  double range)
	{
		dispatcher.sendToAllAround(message, new NetworkRegistry.TargetPoint(dimension, x, y, z,
				range));
	}

	/**
	 * Sends a message to everyone within a certain range of the player
	 * provided.
	 */
	public final void sendToAllAround(IMessage message, EntityPlayer player, double range)
	{
		this.sendToAllAround(message,
				player.worldObj.provider.getDimensionId(),
				player.posX,
				player.posY,
				player.posZ,
				range);
	}

	/**
	 * Send this message to everyone within the supplied dimension. See
	 * {@link SimpleNetworkWrapper#sendToDimension(IMessage, int)}
	 */
	public final void sendToDimension(IMessage message, int dimensionId)
	{
		dispatcher.sendToDimension(message, dimensionId);
	}

	/**
	 * Send this message to the server. See
	 * {@link SimpleNetworkWrapper#sendToServer(IMessage)}
	 */
	public final void sendToServer(IMessage message)
	{
		dispatcher.sendToServer(message);
	}

}
