package net.simplelib.server;

import net.minecraft.command.ICommand;
import net.minecraftforge.fml.common.eventhandler.Event;

/**
 * @author ci010
 */
public interface CommandAccess
{
	Event.Result canExecute(ICommand cmd, String[] args);
}
