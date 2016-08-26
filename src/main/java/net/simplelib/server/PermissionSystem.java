package net.simplelib.server;

import api.simplelib.capabilities.Capabilities;
import api.simplelib.registry.ModHandler;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.management.UserListOpsEntry;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.event.CommandEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;

/**
 * @author ci010
 */
@ModHandler
public class PermissionSystem
{
	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void onCmdExe(CommandEvent event)
	{
		ICommandSender sender = event.getSender();
		if (sender instanceof EntityPlayerMP)
		{
			EntityPlayerMP mp = (EntityPlayerMP) sender;
			PlayerPermission capability = mp.getCapability(PlayerPermission.CAPABILITY, null);
			Event.Result result = capability.canExecute(event.getCommand(), event.getParameters());
			if (result == Event.Result.DENY)
				event.setCanceled(true);
			else if (result == Event.Result.DEFAULT)
				if (!event.getCommand().checkPermission(mp.mcServer, mp))
					event.setCanceled(true);
		}
	}

	static
	{
		CapabilityManager.INSTANCE.register(PlayerPermission.class, Capabilities.<PlayerPermission>emptyStorage(),
				new Callable<PlayerPermission>()
				{
					@Override
					public PlayerPermission call() throws Exception
					{
						return new PlayerPermissionImpl();
					}
				});
	}

	/**
	 * Delegate to event.
	 */
	public static boolean hasPermissionHook(EntityPlayer player, int permLevel, String cmd)
	{
		String modId = CommandManager.INSTANCE.getModId(cmd);
		if (modId == null)
			modId = "minecraft";
		EntityPlayerMP mp = (EntityPlayerMP) player;
		if (modId.equals("minecraft"))
			if ("seed".equals(cmd) && !mp.mcServer.isDedicatedServer())
				return true;
			else if (!"tell".equals(cmd) && !"help".equals(cmd) && !"me".equals(cmd) && !"trigger".equals(cmd))
				if (mp.mcServer.getPlayerList().canSendCommands(player.getGameProfile()))
				{
					UserListOpsEntry userlistopsentry = mp.mcServer.getPlayerList().getOppedPlayers().getEntry(player.getGameProfile());
					return userlistopsentry != null ? userlistopsentry.getPermissionLevel() >= permLevel : mp.mcServer.getOpPermissionLevel() >= permLevel;
				}
				else return false;
			else return true;
		return true;
	}

	public static class PlayerPermissionImpl implements PlayerPermission
	{
		private Map<CommandAccess, CommandAccess> accesses;
		private Set<Group> groupSet;
		private Set<ResourceLocation> customPerm = Sets.newHashSet();

		@Override
		public Collection<CommandAccess> getCommandAccesses()
		{
			return accesses.values();
		}

		@Override
		public void addAccess(CommandAccess access)
		{
			if (accesses == null)
				accesses = Maps.newHashMap();
			CommandAccess last = accesses.put(access, access);
			if (last != null)
				if (access instanceof CmdAccessNode && last instanceof CmdAccessNode)
					((CmdAccessNode) access).merge((CmdAccessNode) last);
		}

		@Override
		public boolean removeAccess(CommandAccess access)
		{
			CommandAccess remove = accesses.remove(access);
			return remove != null;
		}

		@Override
		public Set<Group> getGroups()
		{
			if (groupSet == null)
				groupSet = Sets.newHashSet();
			return groupSet;
		}

		@Override
		public void addCustomPermission(ResourceLocation loc)
		{
			customPerm.add(loc);
		}

		@Override
		public boolean hasCustomPermission(ResourceLocation loc)
		{
			return customPerm.contains(loc);
		}

		@Override
		public void removeCustomPermission(ResourceLocation loc)
		{
			customPerm.remove(loc);
		}

		@Override
		public Event.Result canExecute(ICommand cmd, String[] args)
		{
			Event.Result temp = Event.Result.DEFAULT;
			if (accesses != null)
				for(CommandAccess commandAccess : accesses.values())
				{
					temp = commandAccess.canExecute(cmd, args);
					if (temp != Event.Result.DEFAULT)
						return temp;
				}
			for(Group group : groupSet)
			{
				temp = group.canExecute(cmd, args);
				if (temp != Event.Result.DEFAULT)
					return temp;
			}
			return temp;
		}
	}
}
