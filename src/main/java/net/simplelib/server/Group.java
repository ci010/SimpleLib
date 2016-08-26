package net.simplelib.server;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import net.minecraft.command.ICommand;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.eventhandler.Event;

import java.util.Map;
import java.util.Set;

/**
 * @author ci010
 */
public class Group implements CommandAccess
{
	private int permLevel;
	private Map<CommandAccess, CommandAccess> accesses;
	private Set<ResourceLocation> customPerm = Sets.newHashSet();

	public Group(Group parent)
	{
		accesses = Maps.newHashMap(parent.accesses);
		permLevel = parent.getPermLevel();
	}

	public Group(int permLevel)
	{
		this.permLevel = permLevel;
		this.accesses = Maps.newHashMap();
	}

	public int getPermLevel()
	{
		return permLevel;
	}

	public void addAccess(CommandAccess access)
	{
		CommandAccess last = accesses.put(access, access);
		if (last != null)
			if (access instanceof CmdAccessNode && last instanceof CmdAccessNode)
				((CmdAccessNode) access).merge((CmdAccessNode) last);
	}

	@Override
	public Event.Result canExecute(ICommand cmd, String[] args)
	{
		Event.Result temp;
		if (accesses != null && !accesses.isEmpty())
			for(CommandAccess access : accesses.values())
				if ((temp = access.canExecute(cmd, args)) != Event.Result.DEFAULT)
					return temp;
		return Event.Result.DEFAULT;
	}

	public void addCustomPermission(ResourceLocation loc)
	{
		customPerm.add(loc);
	}

	public boolean hasCustomPermission(ResourceLocation loc)
	{
		return customPerm.contains(loc);
	}

	public void removeCustomPermission(ResourceLocation loc)
	{
		customPerm.remove(loc);
	}
}
