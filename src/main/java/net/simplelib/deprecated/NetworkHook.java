package net.simplelib.deprecated;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/**
 * @author ci010
 */
public class NetworkHook
{
	public static EntityPlayer getPlayer(MessageContext ctx)
	{
		if (ctx.side.isClient())
			return client();
		else
			return ctx.getServerHandler().playerEntity;
	}

	private static EntityPlayer client()
	{
		return  Minecraft.getMinecraft().thePlayer;
	}
}
