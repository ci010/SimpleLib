package api.simplelib.network;

import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

/**
 * @author ci010
 */
public class NetworkWrapper extends SimpleNetworkWrapper
{
	private boolean[] set = new boolean[256];
	private byte current = 0;

	public static NetworkWrapper newNetwork(String id)
	{
		return new NetworkWrapper(id);
	}

	public NetworkWrapper(String channel)
	{
		super(channel);
	}

	public <REQ extends IMessage> void register(AbstractMessageHandler<? super REQ> messageHandler, Class<REQ> requestMessageType)
	{
		if (messageHandler instanceof AbstractClientMessageHandler)
		{
			validate();
			registerMessage(messageHandler, requestMessageType, current, Side.CLIENT);
			++current;
		}
		else if (messageHandler instanceof AbstractServerMessageHandler)
		{
			validate();
			registerMessage(messageHandler, requestMessageType, current, Side.SERVER);
			++current;
		}
		else if (messageHandler instanceof AbstractBiMessageHandler)
		{
			validate();
			registerMessage(messageHandler, requestMessageType, current, Side.CLIENT);
			registerMessage(messageHandler, requestMessageType, current, Side.SERVER);
			++current;
		}
		else throw new IllegalArgumentException("Unknown type of MessageHandler? This should not happen.");
	}

	private void validate()
	{
		byte b = current;
		while (set[b]) ++b;
		current = b;
	}

	@Override
	public <REQ extends IMessage, REPLY extends IMessage> void registerMessage(IMessageHandler<? super REQ, ? extends REPLY> messageHandler, Class<REQ> requestMessageType, int discriminator, Side side)
	{
		if (set[discriminator])
			throw new IllegalArgumentException(String.format("Cannot register %s message with %s to id %s as it " +
					"already been registered.", requestMessageType, messageHandler, discriminator));
		set[discriminator] = true;
		super.registerMessage(messageHandler, requestMessageType, discriminator, side);
	}

	@Override
	public <REQ extends IMessage, REPLY extends IMessage> void registerMessage(Class<? extends IMessageHandler<REQ, REPLY>> messageHandler, Class<REQ> requestMessageType, int discriminator, Side side)
	{
		if (set[discriminator])
			throw new IllegalArgumentException(String.format("Cannot register %s message with %s to id %s as it " +
					"already been registered.", requestMessageType, messageHandler, discriminator));
		set[discriminator] = true;
		super.registerMessage(messageHandler, requestMessageType, discriminator, side);
	}
}
