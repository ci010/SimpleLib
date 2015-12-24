package net.simplelib.registry.delegate;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ServerCommandManager;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLServerStartedEvent;
import net.simplelib.abstracts.RegistryDelegate;
import net.simplelib.annotation.type.ASMDelegate;
import net.simplelib.annotation.type.ModCommand;

/**
 * @author ci010
 */
@ASMDelegate
public class CommandDelegate extends RegistryDelegate<ModCommand>
{
	@Mod.EventHandler
	public void onServerStart(FMLServerStartedEvent event)
	{
		try
		{
			((ServerCommandManager) MinecraftServer.getServer().getCommandManager()).registerCommand((CommandBase) this
					.getAnnotatedClass().newInstance());
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
