package net.simplelib.time;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.WorldProviderSurface;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.DummyModContainer;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.LoadController;
import net.minecraftforge.fml.common.ModMetadata;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.network.NetworkCheckHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.simplelib.common.registry.annotation.type.ModCommand;

import java.io.File;
import java.util.Map;

/**
 * @author ci010
 */
//@ModHandler
public class TimeModContainer extends DummyModContainer
{
	public static final String MODID = "Time", NAME = "TimeController", VERSION = "0.1";

	public TimeModContainer()
	{
		super(new ModMetadata());
		ModMetadata meta = this.getMetadata();
		meta.modId = MODID;
		meta.authorList.add("CI010");
		meta.version = VERSION;
		meta.name = NAME;
		meta.description = "A mod that makes some tweaks on Minecraft world time.";
	}

	@Override
	public boolean registerBus(EventBus bus, LoadController controller)
	{
		bus.register(this);
		return true;
	}

	@NetworkCheckHandler
	public boolean check(Map<String, String> modList, Side side)
	{
		DimensionManager.unregisterProviderType(0);
		DimensionManager.unregisterDimension(0);
		if (modList.containsKey(this.getModId()))
			DimensionManager.registerProviderType(0, WorldProviderModified.class, true);
		else
			DimensionManager.registerProviderType(0, WorldProviderSurface.class, true);
		DimensionManager.registerDimension(0, 0);
		return true;
	}

	@Subscribe
	public void init(FMLInitializationEvent event)
	{
		MinecraftForge.EVENT_BUS.register(this);
		FMLCommonHandler.instance().bus().register(this);
	}

	public static class WorldProviderModified extends WorldProviderSurface
	{
		private TimeController time;

		public float calculateCelestialAngle(long worldTime, float partialTick)
		{
			int current = (int) (worldTime % time.getTickPerDay());
			float angle = ((float) current + partialTick) / time.getTickPerDay() - 0.25F;

			if (angle < 0.0F)
				++angle;

			if (angle > 1.0F)
				--angle;

			float temp = angle;
			angle = 1.0F - (float) ((Math.cos((double) angle * Math.PI) + 1.0D) / 2.0D);
			angle = temp + (angle - temp) / 3.0F;
			return angle;
		}

		public TimeController getController()
		{
			return time;
		}
	}

	@SubscribeEvent
	public void onWorldLoad(WorldEvent.Load event)
	{
		if (event.world.provider instanceof WorldProviderModified)
		{
			TimeController controller = new TimeController(event.world);
			if (event.world.isRemote)
			{
				controller.addColor("spring", new ColorModify() {
					@Override
					public int processColor(int color)
					{
						return 0;
					}
				});
			}
			else
				((WorldProviderModified) event.world.provider).time = controller;
		}
	}

	@Override
	public File getSource()
	{
		return null;
	}

	@SubscribeEvent
	public void onWorldTick(TickEvent.WorldTickEvent event)
	{
		if ((event.world.provider instanceof WorldProviderModified) && event.side == Side.SERVER)
			((WorldProviderModified) event.world.provider).getController().tick(event.world.getWorldTime());
	}

	@ModCommand
	public static class GetTime extends CommandBase
	{
		@Override
		public String getCommandName()
		{
			return "getTime";
		}

		@Override
		public String getCommandUsage(ICommandSender sender)
		{
			return "";
		}

		@Override
		public void processCommand(ICommandSender sender, String[] args) throws CommandException
		{
			sender.addChatMessage(new ChatComponentText(String.valueOf(sender.getEntityWorld().getWorldTime())));
			sender.addChatMessage(new ChatComponentText(String.valueOf(sender.getEntityWorld().getTotalWorldTime())));
		}
	}
}
