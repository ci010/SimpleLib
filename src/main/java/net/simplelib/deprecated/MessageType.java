package net.simplelib.deprecated;

import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

/**
 * @author ci010
 */
@Deprecated
enum MessageType
{
	SERVER(IServerMessage.class), CLIENT(IClientMessage.class), FULL(IFullMessage.class);

	public static MessageType getType(IMessage message)
	{
		return message instanceof IFullMessage ?
				FULL :
				message instanceof IServerMessage ?
						SERVER :
						message instanceof IClientMessage ?
								CLIENT : null;
	}

	public final String className, classType;

	MessageType(Class clz)
	{
		className = clz.getName().replace("\\.", "/");
		classType = "L".concat(className).concat(";");
	}
}
