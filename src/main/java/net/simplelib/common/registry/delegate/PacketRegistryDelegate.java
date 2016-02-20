package net.simplelib.common.registry.delegate;

import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import api.simplelib.registry.ASMRegistryDelegate;
import net.simplelib.common.registry.annotation.type.ASMDelegate;
import api.simplelib.network.ModMessage;
import net.simplelib.network.ModNetwork;

/**
 * @author ci010
 */
@ASMDelegate
public class PacketRegistryDelegate extends ASMRegistryDelegate<ModMessage>
{
	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		if (IMessage.class.isAssignableFrom(this.getAnnotatedClass()))
			try
			{
				IMessage msg = (IMessage) this.getAnnotatedClass().newInstance();
				ModNetwork.instance().registerMessage(msg);
			}
			catch (InstantiationException e)
			{
				e.printStackTrace();
			}
			catch (IllegalAccessException e)
			{
				e.printStackTrace();
			}
	}
}
