package net.simplelib.time;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.simplelib.common.nbt.ITagSerial;
import api.simplelib.FileReference;
import api.simplelib.network.ModNetwork;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author ci010
 */
public class TimeController implements ITagSerial
{
	private final World world;
	private long periodTick = 24000L;
	private List<Period> periodList = Lists.newLinkedList();
	private Period current;
	private int currentIdx, year;

	public Period getCurrent()
	{
		return current;
	}

	TimeController(World world)
	{
		this.world = world;
	}

	void fallbackSetting()
	{
		this.setTimePeriod(48000L);
		this.addPeriod("spring", 10);
		this.addPeriod("summer", 10);
		this.addPeriod("fall", 10);
		this.addPeriod("winter", 10);
		this.current = periodList.get(0);
	}

	public void setTimePeriod(long tickPerDay)
	{
		periodTick = tickPerDay;
	}

	public long getTickPerDay()
	{
		return periodTick;
	}

	public Period getCurrentPeriod(World world)
	{
		return this.current;
	}

	public void insertPeriod(int index, String id, int dayPerPeriod)
	{
		this.periodList.add(index, new Period(id, dayPerPeriod));
	}

	public Period addPeriod(String id, int dayPerPeriod)
	{
		Period p = new Period(id, dayPerPeriod);
		this.periodList.add(p);
		return p;
	}

	@SideOnly(Side.CLIENT)
	private Map<String, ColorModify> colorMap;

	@SideOnly(Side.CLIENT)
	public void addColor(String period, ColorModify modify)
	{
		if (colorMap == null)
			colorMap = Maps.newHashMap();
		colorMap.put(period, modify);
	}

	@SideOnly(Side.CLIENT)
	public int modifyColor(int color)
	{
		if (current == null)
			return color;
		ColorModify modify = colorMap.get(current.getId());
		if (modify == null)
			return color;
		return modify.processColor(color);
	}

	@Override
	public void readFromNBT(NBTTagCompound tag)
	{
		NBTTagList periods = tag.getTagList("periods", 10);
		for (int i = 0; i < periods.tagCount(); ++i)
		{
			NBTTagCompound nbt = (NBTTagCompound) periods.get(i);
			Period p = new Period();
			p.readFromNBT(nbt);
			if (periodList.size() == i)
				this.periodList.add(p);
			else
				this.periodList.set(i, p);
		}
		this.currentIdx = tag.getInteger("current");
		this.current = this.periodList.get(currentIdx);
		this.periodTick = tag.getLong("periodTick");
	}

	@Override
	public void writeToNBT(NBTTagCompound tag)
	{
		NBTTagList lst = new NBTTagList();
		for (Period period : this.periodList)
		{
			NBTTagCompound info = new NBTTagCompound();
			period.writeToNBT(info);
			lst.appendTag(info);
		}
		tag.setTag("periods", lst);
		tag.setInteger("current", this.currentIdx);
		tag.setInteger("year", year);
		tag.setLong("periodTick", periodTick);
	}

	class Period implements ITagSerial
	{
		private String id;
		private int dayPerPeriod;
		private int currentDay;

		Period()
		{}

		public Period(String id, int dayPerPeriod)
		{
			this.id = id;
			this.dayPerPeriod = dayPerPeriod;
		}

		boolean tickDay()
		{
			++currentDay;
			MinecraftForge.EVENT_BUS.post(new TimeEvent.NewDay(world, this));
			if (currentDay >= dayPerPeriod)
			{
				currentDay = 0;
				return true;
			}
			return false;
		}

		public int currentDay()
		{
			return this.currentDay;
		}

		public int totalDay()
		{
			return this.dayPerPeriod;
		}

		public String getId()
		{
			return id;
		}

		@Override
		public void readFromNBT(NBTTagCompound tag)
		{
			this.id = tag.getString("id");
			this.dayPerPeriod = tag.getInteger("dayPerPeriod");
			this.currentDay = tag.getInteger("currentDay");
		}

		@Override
		public void writeToNBT(NBTTagCompound tag)
		{
			tag.setString("id", this.id);
			tag.setInteger("dayPerPeriod", dayPerPeriod);
			tag.setInteger("currentDay", currentDay);
		}
	}

	protected void tick(long worldTime)
	{
		if (current == null)
			current = periodList.get(0);
		if (worldTime / this.getTickPerDay() == 0)
			this.newDay();
	}

	void newDay()
	{
		boolean newPeriod = current.tickDay();
		MinecraftForge.EVENT_BUS.post(new TimeEvent.NewDay(world, current));
		if (newPeriod)
		{
			boolean year = false;
			if (++currentIdx == periodList.size())
			{
				currentIdx = 0;
				year = true;
			}
			current = periodList.get(currentIdx);
			MinecraftForge.EVENT_BUS.post(new TimeEvent.NewPeriod(world, current));
			if (year)
				MinecraftForge.EVENT_BUS.post(new TimeEvent.NewYear(world, current, ++this.year));
		}
	}

	@SubscribeEvent
	public void onWorldTick(TickEvent.WorldTickEvent event)
	{
		if (!this.world.isRemote && event.phase == TickEvent.Phase.END)
			this.tick(event.world.getWorldTime());
	}

	@SubscribeEvent
	public void onNewDay(TimeEvent.NewDay day)
	{
		if (!this.world.isRemote)
			ModNetwork.instance().sendToDimension(new MessageNewDay(), this.world.provider.getDimensionId());
	}

	@SubscribeEvent
	public void worldSave(WorldEvent.Save event)
	{
		if (this.world.provider instanceof TimeMod.WorldProviderModified)
		{
			NBTTagCompound tag = new NBTTagCompound();
			this.writeToNBT(tag);
			try
			{
				File file = new File(FileReference.getSave(), "time");
				if (!file.exists())
					file.createNewFile();
				CompressedStreamTools.write(tag, file);
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}
}
