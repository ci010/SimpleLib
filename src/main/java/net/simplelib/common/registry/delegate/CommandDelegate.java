package net.simplelib.common.registry.delegate;

import api.simplelib.Local;
import api.simplelib.command.ISimpleCommand;
import api.simplelib.command.ModCommand;
import api.simplelib.registry.ASMRegistryDelegate;
import net.minecraft.command.*;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.simplelib.common.CommonLogger;
import net.simplelib.common.DebugLogger;
import net.simplelib.common.registry.annotation.type.ASMDelegate;

/**
 * @author ci010
 */
@ASMDelegate
public class CommandDelegate extends ASMRegistryDelegate<ModCommand>
{
	@Mod.EventHandler
	public void onServerStart(FMLServerStartingEvent event)
	{
		try
		{
			final Object o = this.getAnnotatedClass().newInstance();
			ICommand cmd;
			if (o instanceof ICommand)
				cmd = (ICommand) o;
			else if (o instanceof ISimpleCommand)
				cmd = new CommandBase()
				{
					@Override
					public String getCommandName()
					{
						return ((ISimpleCommand) o).name();
					}

					@Override
					public String getCommandUsage(ICommandSender sender)
					{
						return "commands.".concat(this.getCommandName()).concat(".usage");
					}

					@Override
					public void processCommand(ICommandSender sender, String[] args) throws CommandException
					{
						((ISimpleCommand) o).processCommand(sender, args);
					}
				};
			else
			{
				return;
			}
			final String commandUsage = cmd.getCommandUsage(null);
			if (commandUsage != null)
				Local.trans(commandUsage);
			DebugLogger.info("Register the command [/{}] <- [{}:{}].", cmd.getCommandName(),
					this.getModid(), this.getAnnotatedClass());
			((ServerCommandManager) MinecraftServer.getServer().getCommandManager()).registerCommand(cmd);
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
