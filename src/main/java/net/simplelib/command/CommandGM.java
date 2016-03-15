package net.simplelib.command;

import api.simplelib.command.ModCommand;
import com.google.common.collect.Lists;
import net.minecraft.command.CommandGameMode;

import java.util.List;

/**
 * @author ci010
 */
@ModCommand
public class CommandGM extends CommandGameMode
{
	@Override
	public List getCommandAliases()
	{
		return Lists.newArrayList("gm");
	}
}
