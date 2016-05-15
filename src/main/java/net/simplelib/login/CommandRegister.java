package net.simplelib.login;

import api.simplelib.registry.command.ISimpleCommand;
import api.simplelib.registry.command.ModCommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;

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
