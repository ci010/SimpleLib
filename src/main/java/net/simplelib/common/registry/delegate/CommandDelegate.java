package net.simplelib.common.registry.delegate;

import api.simplelib.Local;
import api.simplelib.command.ISimpleCommand;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLServerStartedEvent;
import net.simplelib.common.CommonLogger;
import api.simplelib.registry.ASMRegistryDelegate;
import net.simplelib.common.registry.annotation.type.ASMDelegate;
import api.simplelib.command.ModCommand;

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
			Local.translate(cmd.getCommandUsage(null));
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
