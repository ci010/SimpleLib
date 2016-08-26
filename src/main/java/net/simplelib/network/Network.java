package net.simplelib.network;

import api.simplelib.network.obj.MessageDispatcher;
import api.simplelib.network.obj.MessageType;
import com.google.common.collect.Maps;
import io.netty.channel.ChannelFutureListener;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.FMLEmbeddedChannel;
import net.minecraftforge.fml.common.network.FMLOutboundHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;

import java.util.EnumMap;
import java.util.Map;

/**
 * @author ci010
 */
enum Network
{
	INSTANCE;

	private Map<String, NetworkImpl> maps = Maps.newHashMap();

	public <T> MessageDispatcher<T> getDispatcher(String modid, MessageType<T> type)
	{
		NetworkImpl n = maps.get(modid);
		if (n == null)
		{
			n = new NetworkImpl(modid);
			maps.put(modid, n);
		}
		return n.register(type);
	}

	static class NetworkImpl
	{
		private Codec codec;
		private EnumMap<Side, FMLEmbeddedChannel> channels;
		private byte packetId = 0;

		public NetworkImpl(String modid)
		{
			channels = NetworkRegistry.INSTANCE.newChannel(modid, codec = new Codec());
		}

		public <T> MessageDispatcher<T> register(MessageType<T> type)
		{
			codec.register(packetId++, type);
			return new Impl<>(type);
		}

		class Impl<T> implements MessageDispatcher<T>
		{
			private MessageType<T> type;

			public Impl(MessageType<T> type)
			{
				this.type = type;
			}

			@Override
			public MessageType<T> getMessageType()
			{
				return type;
			}

			@Override
			public void sendToAll(T data)
			{
				channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.ALL);
				channels.get(Side.SERVER).writeAndFlush(new HandlerMsgPair<T>(data, type)).addListener(ChannelFutureListener
						.FIRE_EXCEPTION_ON_FAILURE);
			}

			@Override
			public void sendTo(T data, EntityPlayerMP player)
			{
				channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.PLAYER);
				channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGETARGS).set(player);
				channels.get(Side.SERVER).writeAndFlush(new HandlerMsgPair<T>(data, type)).addListener(ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE);
			}

			@Override
			public void sendToAllAround(T data, NetworkRegistry.TargetPoint point)
			{
				channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.ALLAROUNDPOINT);
				channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGETARGS).set(point);
				channels.get(Side.SERVER).writeAndFlush(new HandlerMsgPair<T>(data, type)).addListener(ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE);
			}

			@Override
			public void sendToDimension(T data, int dimensionId)
			{
				channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.DIMENSION);
				channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGETARGS).set(dimensionId);
				channels.get(Side.SERVER).writeAndFlush(new HandlerMsgPair<T>(data, type)).addListener(ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE);
			}

			@Override
			public void sendToServer(T data)
			{
				channels.get(Side.CLIENT).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.TOSERVER);
				channels.get(Side.CLIENT).writeAndFlush(new HandlerMsgPair<T>(data, type)).addListener(ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE);
			}
		}
	}
}
