package net.simplelib.login;

import api.simplelib.utils.NotNull;
import com.google.common.cache.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.WorldSettings;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.simplelib.HelperMod;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author ci010
 */
public class CommonFileLogin implements LoginSystem.Handler, LoginSystem.Strategy, LoginSystem.Source
{
	private Cache<UUID, File> cache;

	@Override
	public String id()
	{
		return "common";
	}

	@Override
	public void setup(Configuration cfg)
	{

	}

	@Override
	public LoginSystem.Data getData(EntityPlayer player)
	{
		File f = this.cache.getIfPresent(player.getUniqueID());
		if (f != null && f.exists())
			return new LoginData();
		return null;
	}

	private class LoginData implements LoginSystem.Data
	{
		UUID id;
		boolean changed;
		String psw;

		@Override
		public String getInfo(String id)
		{
			return null;
		}

		@Override
		public String getPassword()
		{
			if (changed)
			{
				File f = cache.getIfPresent(id);
				if (f == null)
					return "";
				try
				{
					return FileUtils.readFileToString(f);
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
				return "";
			}
			return psw;
		}

		@Override
		public void setPassword(@NotNull String psw)
		{
			File f = cache.getIfPresent(id);
			if (f != null)
			{
				try
				{
					if (!f.exists())
					{
						f.createNewFile();
					}
					FileUtils.write(f, psw);
					this.changed = true;
				}
				catch (IOException e)
				{
				}
			}
		}

		@Override
		public void setInfo(String id, String info)
		{

		}
	}

	public CommonFileLogin()
	{
		MinecraftForge.EVENT_BUS.register(this);
	}

	@SubscribeEvent
	public void onLoad(PlayerEvent.LoadFromFile event)
	{
		if (!HelperMod.proxy.isClient())
		{
			File psw = event.getPlayerFile(".psw");
			if (cache == null)
				this.setExpireTime(5);
			cache.put(event.entityPlayer.getUniqueID(), psw);
		}
	}

	@Override
	public void setExpireTime(int minute)
	{
		this.cache = CacheBuilder.newBuilder()
				.expireAfterAccess(minute, TimeUnit.MINUTES)
				.removalListener(new RemovalListener<UUID, File>()
				{
					@Override
					public void onRemoval(RemovalNotification<UUID, File> notification)
					{
						if (notification.getCause() == RemovalCause.EXPIRED)
							if (notification.getKey() != null)
							{
								UUID key = notification.getKey();
								EntityPlayerMP playerMP =
										(EntityPlayerMP) MinecraftServer.getServer().getEntityWorld().getPlayerEntityByUUID(key);
								playerMP.playerNetServerHandler.kickPlayerFromServer("Too long to login");
							}
					}
				}).build();
	}

	@Override
	public void handlePlayer(EntityPlayer player)
	{
		player.setGameType(WorldSettings.GameType.SPECTATOR);
	}

	@Override
	public LoginSystem.LoginState login(final EntityPlayer player, final String input, LoginSystem.Data data)
	{
		final File f = cache.getIfPresent(player.getUniqueID());
		if (f != null)
			if (f.exists())
				try
				{
					if (input.equals(FileUtils.readFileToString(f)))
						return LoginSystem.LoginState.Success;
					else
						return LoginSystem.LoginState.WrongPassword;
				}
				catch (IOException e)
				{
					return LoginSystem.LoginState.Error;
				}
		return LoginSystem.LoginState.NotRegistered;
	}

	@Override
	public boolean register(EntityPlayer player, String[] input, LoginSystem.Data data)
	{
		File f = cache.getIfPresent(player.getUniqueID());
		if (f != null)
		{
			try
			{
				if (!f.exists())
					f.createNewFile();

			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		return false;
	}
}
