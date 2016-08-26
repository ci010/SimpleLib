package net.simplelib.network;

import api.simplelib.network.obj.MessageType;
import net.minecraft.nbt.NBTTagCompound;

/**
 * @author ci010
 */
class HandlerMsgPair<T>
{
	private T data;
	MessageType<T> tMessageType;

	HandlerMsgPair(T data, MessageType<T> tMessageType)
	{
		this.data = data;
		this.tMessageType = tMessageType;
	}

	NBTTagCompound buildInto()
	{
		return tMessageType.getSerializer().serialize(data);
	}
}
