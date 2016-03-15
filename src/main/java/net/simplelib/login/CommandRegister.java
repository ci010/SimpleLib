package net.simplelib.login;

import api.simplelib.command.ISimpleCommand;
import api.simplelib.command.ModCommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.simplelib.login.LoginSystem;

/**
 * @author ci010
 */
@ModCommand
public class CommandRegister implements ISimpleCommand
{
	@Override
	public String name()
	{
		return "register";
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) throws UsageException
	{
		if (sender instanceof EntityPlayer)
		{
			if (args.length == 2)
				LoginSystem.getInstance().register((EntityPlayer) sender, args);
			else
				throw new UsageException(this.name());
		}
	}
}
