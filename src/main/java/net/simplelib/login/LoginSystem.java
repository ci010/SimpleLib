package net.simplelib.login;

import api.simplelib.utils.Local;
import api.simplelib.Instance;
import api.simplelib.registry.ModHandler;
import api.simplelib.utils.NotNull;
import api.simplelib.utils.Nullable;
import com.google.common.collect.Maps;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.WorldSettings;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.simplelib.HelperMod;

import java.io.File;
import java.util.Map;

/**
 * @author ci010
 */
@ModHandler
public class LoginSystem
{
	@Instance
	private static LoginSystem instance = new LoginSystem();

	public static LoginSystem getInstance()
	{
		return instance;
	}

	private Handler handler;
	private Strategy strategy;
	private Source source;
	private boolean init;
	private Map<String, Handler> handlerMap = Maps.newHashMap();
	private Map<String, Source> sourceMap = Maps.newHashMap();
	private Map<String, Strategy> strategyMap = Maps.newHashMap();

	private LoginSystem()
	{

	}

	public void reigsterHandler(String id, Handler handler)
	{
		handlerMap.put(id, handler);
	}


	public void preInit(FMLPreInitializationEvent event)
	{
		if (init)
			return;
		init = true;
		File dir = event.getModConfigurationDirectory();
		for (Source s : sourceMap.values())
			s.setup(new Configuration(dir, s.id()));
		Configuration cfg = new Configuration(new File(dir, "login"));
		cfg.load();

		String src = cfg.get(Local.trans("cfg.login"), Local.trans("cfg.login.src"), "file",
				Local.trans("cfg.login.src.comment")).getString();
		this.source = this.sourceMap.get(src);
		if (this.source == null)
		{

		}

		String model = cfg.get(Local.trans("cfg.login"), Local.trans("cfg.login.mode"), "direct",
				Local.trans("cfg.login.mode.comment")).getString();
		this.handler = handlerMap.get(model);
		if (handler == null)
			handler = new CommonFileLogin();

		String strategy = cfg.get(Local.trans("cfg.login"), Local.trans("cfg.login.strategy"), "specter",
				Local.trans("cfg.login.strategy.comment")).getString();
		this.strategy = strategyMap.get(strategy);
		if (this.strategy == null)
			if (this.handler instanceof CommandLogin)
				this.strategy = (Strategy) this.handler;
			else this.strategy = new CommonFileLogin();

		int i = cfg.get(Local.trans("cfg.login"), Local.trans("cfg.login.expired"), 5,
				Local.trans("cfg.login.expired.comment")).getInt();
		this.strategy.setExpireTime(i);
		cfg.save();
		handlerMap = null;
	}

	public void login(final EntityPlayer player, final String psw)
	{
		switch (handler.login(player, psw, source.getData(player)))
		{
			case WrongPassword:
				player.addChatComponentMessage(Local.newChat("command.login.wrong"));
				break;
			case NotRegistered:
				player.addChatComponentMessage(Local.newChat("command.login.register"));
				break;
			case Success:
				player.addChatComponentMessage(Local.newChat("command.login.success"));
				player.setGameType(WorldSettings.GameType.SURVIVAL);//TODO
				break;
			case Error:
			default:
				player.addChatComponentMessage(Local.newChat("command.login.error"));
		}
	}

	public void register(EntityPlayer player, String[] input)
	{
		if (handler.register(player, input, source.getData(player)))
			player.addChatComponentMessage(Local.newChat("command.login.usage"));
		else
			player.addChatComponentMessage(Local.newChat("command.register.usage"));
	}

	@SubscribeEvent
	public void onLogin(net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent event)
	{
		if (!HelperMod.proxy.isClient())
			strategy.handlePlayer(event.player);
	}

	public interface Handler
	{
		LoginState login(EntityPlayer player, String input, Data data);

		boolean register(EntityPlayer player, String[] input, Data data);
	}

	public interface Source
	{
		String id();

		void setup(Configuration cfg);

		@Nullable
		Data getData(EntityPlayer player);
	}

	public interface Strategy
	{
		void setExpireTime(int minute);

		void handlePlayer(EntityPlayer player);
	}

	public interface Data
	{
		@Nullable
		String getInfo(String id);

		String getPassword();

		void setPassword(@NotNull String psw);

		void setInfo(String id, String info);
	}

	public enum LoginState
	{
		WrongPassword, NotRegistered, Success, Error
	}
}
