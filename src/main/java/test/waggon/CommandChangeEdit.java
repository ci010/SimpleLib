package test.waggon;

import api.simplelib.command.ISimpleCommand;
import api.simplelib.command.ModCommand;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;

/**
 * @author ci010
 */
@ModCommand
public class CommandChangeEdit implements ISimpleCommand
{
	@Override
	public String name()
	{
		return "edit";
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) throws CommandException
	{
		if (sender instanceof EntityPlayer)
		{
			System.out.println(((EntityPlayer) sender).capabilities.allowEdit);
			((EntityPlayer) sender).capabilities.allowEdit = false;
			System.out.println(((EntityPlayer) sender).capabilities.allowEdit);
		}
	}
}
