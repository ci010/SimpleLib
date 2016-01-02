package net.simplelib.registry.delegate;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ServerCommandManager;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLServerStartedEvent;
import net.simplelib.common.CommonLogger;
import net.simplelib.registry.abstracts.ASMRegistryDelegate;
import net.simplelib.registry.annotation.type.ASMDelegate;
import net.simplelib.registry.annotation.type.ModCommand;

/**
 * @author ci010
 */
@ASMDelegate
public class CommandDelegate extends ASMRegistryDelegate<ModCommand>
{
	@Mod.EventHandler
	public void onServerStart(FMLServerStartedEvent event)
	{
		try
		{
			((ServerCommandManager) MinecraftServer.getServer().getCommandManager()).registerCommand((CommandBase) this
					.getAnnotatedClass().newInstance());
			CommonLogger.info("Register the command {} by mod [{}].", this.getAnnotatedClass().getSimpleName(), this.getModid());
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
