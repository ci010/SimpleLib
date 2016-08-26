package net.simplelib.network;

import api.simplelib.network.obj.MessageContext;
import api.simplelib.network.obj.MessageType;
import api.simplelib.utils.DebugLogger;
import gnu.trove.map.hash.TByteObjectHashMap;
import gnu.trove.map.hash.TObjectByteHashMap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;
import io.netty.util.Attribute;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetHandler;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.FMLIndexedMessageToMessageCodec;
import net.minecraftforge.fml.common.network.FMLOutboundHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.internal.FMLProxyPacket;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * @author ci010
 */
class Codec extends MessageToMessageCodec<FMLProxyPacket, HandlerMsgPair>
{
	private TByteObjectHashMap<MessageType> discriminators = new TByteObjectHashMap<>();
	private TObjectByteHashMap<MessageType> types = new TObjectByteHashMap<>();

	@Override
	public void handlerAdded(ChannelHandlerContext ctx) throws Exception
	{
		super.handlerAdded(ctx);
		ctx.attr(FMLIndexedMessageToMessageCodec.INBOUNDPACKETTRACKER).set(new
				ThreadLocal<WeakReference<FMLProxyPacket>>());
	}

	public void register(byte id, MessageType type)
	{
		discriminators.put(id, type);
		types.put(type, id);
	}

	@Override
	protected void encode(ChannelHandlerContext ctx, HandlerMsgPair msg, List<Object> out) throws Exception
	{
		NBTTagCompound tag = msg.buildInto();
		byte id = types.get(msg.tMessageType);

		PacketBuffer buffer = new PacketBuffer(Unpooled.buffer());
		buffer.writeByte(id);
		buffer.writeNBTTagCompoundToBuffer(tag);

		FMLProxyPacket proxy = new FMLProxyPacket(buffer/*.copy()*/, ctx.channel().attr(NetworkRegistry.FML_CHANNEL).get());
		WeakReference<FMLProxyPacket> ref = ctx.attr(FMLIndexedMessageToMessageCodec.INBOUNDPACKETTRACKER).get().get();
		FMLProxyPacket old = ref == null ? null : ref.get();
		if (old != null)
			proxy.setDispatcher(old.getDispatcher());
		out.add(proxy);
	}

	@Override
	protected void decode(ChannelHandlerContext ctx, FMLProxyPacket msg, List<Object> out) throws Exception
	{
		ByteBuf payload = msg.payload().duplicate();
		if (payload.readableBytes() < 1)
			DebugLogger.fatal("The FMLIndexedCodec has received an empty buffer on channel %s, likely a result of a" +
					" LAN server issue. Pipeline parts : %s", ctx.channel().attr(NetworkRegistry.FML_CHANNEL), ctx.pipeline().toString());
		byte discriminator = payload.readByte();
		MessageType type = discriminators.get(discriminator);
		if (type == null)
			throw new NullPointerException("Undefined message for discriminator " + discriminator + " in channel " + msg.channel());

		ctx.attr(FMLIndexedMessageToMessageCodec.INBOUNDPACKETTRACKER).get().set(new WeakReference<FMLProxyPacket>(msg));

		NBTTagCompound tag = ByteBufUtils.readTag(payload.slice());
		Object obj = type.getSerializer().deserialize(tag);

		Attribute<Side> attr = ctx.attr(NetworkRegistry.CHANNEL_SOURCE);
		Side side = attr.get();
		if (side == null)
		{
			return;
		}
		if (side.isClient())
		{
			ContextImpl context = new ContextImpl(getClient(),
					ctx.channel().attr(NetworkRegistry.NET_HANDLER).get(), side, ctx);
			type.handleClient(context, obj);
		}
		else
		{
			NetHandlerPlayServer netHandler = (NetHandlerPlayServer) ctx.channel().attr(NetworkRegistry.NET_HANDLER).get();
			ContextImpl context = new ContextImpl(netHandler.playerEntity, netHandler, side, ctx);
			type.handleServer(context, obj);
		}

		out.add(obj);
	}

	private class ContextImpl implements MessageContext
	{
		EntityPlayer player;
		World world;
		INetHandler handler;
		Side side;
		ChannelHandlerContext ctx;

		public ContextImpl(EntityPlayer player, INetHandler handler, Side side, ChannelHandlerContext ctx)
		{
			this.player = player;
			this.world = player.getEntityWorld();
			this.handler = handler;
			this.side = side;
			this.ctx = ctx;
		}

		@Override
		public <T> void response(MessageType<T> handler, T data)
		{
			ctx.channel().attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.REPLY);
			ctx.writeAndFlush(new HandlerMsgPair<T>(data, handler)).addListener(ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE);
		}

		@Override
		public EntityPlayer getPlayer()
		{
			return player;
		}

		@Override
		public World getWorld()
		{
			return world;
		}

		@Override
		public INetHandler getNetHandler()
		{
			return handler;
		}

		@Override
		public Side getSide()
		{
			return side;
		}
	}

	@SideOnly(Side.CLIENT)
	private EntityPlayer getClient()
	{
		return Minecraft.getMinecraft().thePlayer;
	}
}
