package net.ci010.minecrafthelper;

import com.google.common.collect.Iterators;
import com.google.common.collect.Sets;
import net.minecraft.command.CommandBase;

import java.util.Iterator;
import java.util.Set;

/**
 * @author ci010
 */
public class CommandCache extends RegisterCache<CommandBase>
{
	private static CommandCache instance;

	private Set<Class<? extends CommandBase>> unregistered = Sets.newHashSet();

	public static CommandCache instance()
	{
		if (instance == null)
			instance = new CommandCache();
		return instance;
	}

	void addCommand(Class<? extends CommandBase> clz)
	{
		this.unregistered.add(clz);
	}

	@Override
	public Iterator<CommandBase> iterator()
	{
		Set<CommandBase> temp = Sets.newHashSet();
		for (Class<? extends CommandBase> src : unregistered)
			try
			{
				temp.add(src.newInstance());
			}
			catch (InstantiationException e)
			{
				e.printStackTrace();
			}
			catch (IllegalAccessException e)
			{
				e.printStackTrace();
			}
		return Iterators.concat(temp.iterator(), super.iterator());
	}
}
