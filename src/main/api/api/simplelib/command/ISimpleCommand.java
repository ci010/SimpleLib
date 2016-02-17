package api.simplelib.command;

import net.minecraft.command.ICommandSender;

/**
 * @author ci010
 */
public interface ISimpleCommand
{
	String name();

	void processCommand(ICommandSender sender, String[] args);
}
