package net.simplelib.time;

import api.simplelib.utils.FileReference;
import api.simplelib.registry.command.ModCommand;
import api.simplelib.network.ModNetwork;
import api.simplelib.utils.GenericUtil;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import net.minecraft.client.AnvilConverterException;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.WorldProviderSurface;
import net.minecraft.world.storage.SaveFormatComparator;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.terraingen.BiomeEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.DummyModContainer;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.LoadController;
import net.minecraftforge.fml.common.ModMetadata;
import net.minecraftforge.fml.common.event.FMLConstructionEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkCheckHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author ci010
 */
public class TimeMod extends DummyModContainer
{
	public static final String MODID = "Time", NAME = "TimeController", VERSION = "0.1";

	public TimeMod()
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

	@Override
	public Object getMod()
	{
		return this;
	}

	@Subscribe
	public void construct(FMLConstructionEvent event)
	{
		NetworkRegistry.INSTANCE.register(this, this.getClass(), "", event.getASMHarvestedData());
	}

	@NetworkCheckHandler
	public boolean check(Map<String, String> modList, Side side)
	{
		if (side == Side.CLIENT)
			return true;
		DimensionManager.unregisterProviderType(0);
		DimensionManager.unregisterDimension(0);
		if (modList.containsKey(this.getModId()) || side == Side.CLIENT)
			DimensionManager.registerProviderType(0, WorldProviderModified.class, true);
		else
			DimensionManager.registerProviderType(0, WorldProviderSurface.class, true);
		DimensionManager.registerDimension(0, 0);
		return true;
	}

	@Subscribe
	public void init(FMLInitializationEvent event)
	{
		DimensionManager.unregisterProviderType(0);
		DimensionManager.unregisterDimension(0);
		DimensionManager.registerProviderType(0, WorldProviderModified.class, true);
		DimensionManager.registerDimension(0, 0);
		MinecraftForge.EVENT_BUS.register(this);
		FMLCommonHandler.instance().bus().register(this);
	}

	public static class WorldProviderModified extends WorldProviderSurface
	{
		private TimeController time;

		public float calculateCelestialAngle(long worldTime, float partialTick)
		{
			long tickPerDay = 48000L;
			if (time != null)
				tickPerDay = time.getTickPerDay();
			int current = (int) (worldTime % tickPerDay);
			float angle = ((float) current + partialTick) / tickPerDay - 0.25F;

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

		public void setController(TimeController controller)
		{
			this.time = controller;
		}
	}

	@SubscribeEvent
	public void onWorldLoad(WorldEvent.Load event)
	{
		if (event.world.provider instanceof WorldProviderModified)
		{
			File saveDir = null;
			if (FMLCommonHandler.instance().getSide() == Side.CLIENT)
			{
				try
				{
					List<SaveFormatComparator> saveList = GenericUtil.cast(Minecraft.getMinecraft().getSaveLoader().getSaveList());
					for (SaveFormatComparator token : saveList)
						if (event.world.getWorldInfo().getWorldName().equals(token.getDisplayName()))
							saveDir = new File(FileReference.getSave(), token.getFileName());
				}
				catch (AnvilConverterException e)
				{
					e.printStackTrace();
				}
			}
			if (saveDir == null)
				saveDir = FileReference.getSave();
			TimeController controller = new TimeController(event.world);
			try
			{
				NBTTagCompound tag = CompressedStreamTools.read(new File(saveDir, "time"));
				if (tag != null)
					controller.readFromNBT(tag);
				else controller.fallbackSetting();
			}
			catch (IOException e)
			{
				controller.fallbackSetting();
			}
			((WorldProviderModified) event.world.provider).setController(controller);
			MinecraftForge.EVENT_BUS.register(controller);
			MinecraftForge.EVENT_BUS.post(new TimeEvent.Init(event.world, controller, controller.getCurrent()));
			if (!event.world.isRemote)
				ModNetwork.instance().sendToDimension(new MessageTimeSync(controller), event.world.provider
						.getDimensionId());
		}
	}

	@SubscribeEvent
	public void onGetGrassColor(BiomeEvent.GetGrassColor event)
	{
		event.newColor = Hook.provider.getController().modifyColor(event.originalColor);
	}

	@SubscribeEvent
	public void onGetFoColor(BiomeEvent.GetFoliageColor event)
	{
		event.newColor = Hook.provider.getController().modifyColor(event.originalColor);
	}

	@SubscribeEvent
	public void onTimeInit(TimeEvent.Init event)
	{
		if (event.world.isRemote)
		{
			Hook.init((WorldProviderModified) event.world.provider);
			event.getController().addColor("spring", colorSpring);
			event.getController().addColor("summer", colorSummer);
			event.getController().addColor("fall", colorFall);
			event.getController().addColor("winter", coloWinter);
		}
	}

	@SideOnly(Side.CLIENT)
	public static final ColorModify colorSpring = new ColorModify()
	{
		final int rS = 5 << 16, gS = 40 << 8, bS = 10;

		@Override
		public int processColor(int color)
		{
			return color + rS + gS + bS;
//			int r = (color >> 16) & 0xFF, g = (color >> 8) & 0xFF, b = (color) & 0xFF, alpha = color >> 24;
//			alpha = alpha << 24;
//			r = ((int) (r * 1.5)) << 16;
//			g = ((int) (g * 1.1)) << 8;
//			b = ((int) (b * 1.1));
//			return alpha | r | g | b;
		}
	}, colorSummer = new ColorModify()
	{
		@Override
		public int processColor(int color)
		{
			return color;
		}
	}, colorFall = new ColorModify()
	{
		@Override
		public int processColor(int color)
		{
			return color;
		}
	}, coloWinter = new ColorModify()
	{
		@Override
		public int processColor(int color)
		{
			return color;
		}
	};

	@Override
	public File getSource()
	{
		return TimeLoadingPlugin.src;
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
