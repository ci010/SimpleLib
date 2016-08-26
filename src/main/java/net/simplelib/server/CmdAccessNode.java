package net.simplelib.server;

import net.minecraft.command.ICommand;
import net.minecraftforge.fml.common.eventhandler.Event;
import org.apache.commons.lang3.ArrayUtils;

/**
 * @author ci010
 */
public class CmdAccessNode implements CommandAccess
{
	private String domain, cmd, args[];
	private boolean denyMode;

	public CmdAccessNode(String domain, boolean denyMode)
	{
		this.domain = domain;
		this.denyMode = denyMode;
	}

	public CmdAccessNode(String domain, String cmd, String[] args, boolean denyMode)
	{
		this.domain = domain;
		this.cmd = cmd;
		this.args = args;
		this.denyMode = denyMode;
	}

	public boolean merge(CmdAccessNode node)
	{
		if (this.equals(node))
		{
			if (this.args != null)
				if (node.args == null)
					this.args = null;
				else this.args = ArrayUtils.addAll(this.args, node.args);
			return true;
		}
		return false;
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		CmdAccessNode that = (CmdAccessNode) o;

		if (denyMode != that.denyMode) return false;
		if (!domain.equals(that.domain)) return false;
		return cmd != null ? cmd.equals(that.cmd) : that.cmd == null;
	}

	@Override
	public int hashCode()
	{
		int result = domain.hashCode();
		result = 31 * result + (cmd != null ? cmd.hashCode() : 0);
		result = 31 * result + (denyMode ? 1 : 0);
		return result;
	}

	public Event.Result canExecute(ICommand cmd, String[] args)
	{
		String modId = CommandManager.INSTANCE.getModId(cmd);
		if (domain.equals(modId))
			if (this.cmd == null)//wildcard
				return denyMode ? Event.Result.DENY : Event.Result.ALLOW;
			else if (this.cmd.equals(cmd.getCommandName()))
				if (this.args == null)//wildcard
					return denyMode ? Event.Result.DENY : Event.Result.ALLOW;
				else if (args != null && args.length == 0 && ArrayUtils.contains(this.args, args[0]))
					return denyMode ? Event.Result.DENY : Event.Result.ALLOW;
		return Event.Result.DEFAULT;
	}
}
