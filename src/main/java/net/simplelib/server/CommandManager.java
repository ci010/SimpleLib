package net.simplelib.server;

import api.simplelib.Instance;
import api.simplelib.LoadingDelegate;
import com.google.common.collect.Maps;
import net.minecraft.command.ICommand;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerAboutToStartEvent;
import api.simplelib.utils.DebugLogger;
import net.simplelib.registry.RegistryBufferManager;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * @author ci010
 */
@LoadingDelegate
public class CommandManager
{
	@Instance
	public static final CommandManager INSTANCE = new CommandManager();

	private Map<String, String[]> aliasesMap = Maps.newHashMap();
	private Map<String, String> cmdToModId = Maps.newHashMap();

	private Configuration aliasesCfg;

	public void register(String cmd, String... aliases)
	{
		if (aliases == null || aliases.length == 0)
			return;
		aliasesMap.put(cmd, aliases);
	}

	public String getModId(ICommand cmd)
	{
		return getModId(cmd.getCommandName());
	}

	public String getModId(String cmd)
	{
		return cmdToModId.get(cmd);
	}

	@Mod.EventHandler
	public void pre(FMLPreInitializationEvent event)
	{
		aliasesCfg = new Configuration(new File(event.getModConfigurationDirectory(),
				"command_aliases.aliasesCfg"));
	}

	@Mod.EventHandler
	public void serverStart(FMLServerAboutToStartEvent event)
	{
		Map<String, String[]> aliasesMap = Maps.newHashMap(this.aliasesMap);
		Map<String, ICommand> commands = event.getServer().getCommandManager().getCommands();
		aliasesCfg.load();
		for(Map.Entry<String, ICommand> entry : commands.entrySet())
		{
			analyze(entry.getKey(), entry.getValue());
			List<String> list = entry.getValue().getCommandAliases();
			String[] aliases = aliasesCfg.get("aliases", entry.getKey(), list.toArray(new String[list.size()])).getStringList();
			if (aliases != null && aliases.length > 0)
				aliasesMap.put(entry.getKey(), aliases);
		}
		aliasesCfg.save();
		for(Map.Entry<String, String[]> entry : aliasesMap.entrySet())
			if (commands.containsKey(entry.getKey()))
				for(String s : entry.getValue())
					if (!commands.containsKey(s))
						commands.put(s, commands.get(entry.getKey()));
					else
						DebugLogger.fatal("Cannot add aliases [{}] to command [{}] as duplicated command getName!",
								s, entry.getKey());
	}

	private void analyze(String cmdString, ICommand cmd)
	{
		String modid = RegistryBufferManager.instance().getModidByPackage(cmd.getClass());
		if (modid == null) modid = "minecraft";
		cmdToModId.put(cmdString, modid);
	}
}
