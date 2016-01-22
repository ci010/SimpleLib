package net.simplelib.time;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.simplelib.common.registry.annotation.type.ModHandler;

import java.util.List;
import java.util.Map;

/**
 * @author ci010
 */
@ModHandler
public class TimeController
{
	private long periodTick = 24000L;
	private List<Period> periodList = Lists.newLinkedList();
	private Period current;
	private int currentIdx;
	private World world;

	public Period getCurrent()
	{
		return current;
	}

	TimeController(World world)
	{
		this.world = world;
		this.setTimePeriod(48000L);
		this.addPeriod("spring", 10);
		this.addPeriod("summer", 10);
		this.addPeriod("fall", 10);
		this.addPeriod("winter", 10);
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
		MinecraftForge.EVENT_BUS.post(new TimeEvent.RegPeriod());
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

	class Period
	{
		private String id;
		private int dayPerPeriod;
		private int currentDay;

		public Period(String id, int dayPerPeriod)
		{
			this.id = id;
			this.dayPerPeriod = dayPerPeriod;
		}

		public boolean dayPass()
		{
			++currentDay;
			MinecraftForge.EVENT_BUS.post(new TimeEvent.Day());
			if (currentDay >= dayPerPeriod)
			{
				currentDay = 0;
				return true;
			}
			return false;
		}

		public String getId()
		{
			return id;
		}
	}

	protected void tick(long worldTime)
	{
		if (worldTime / this.getTickPerDay() == 0)
			if (current.dayPass())
			{
				boolean year = false;
				if (++currentIdx == periodList.size())
				{
					currentIdx = 0;
					year = true;
				}
				current = periodList.get(currentIdx);
				MinecraftForge.EVENT_BUS.post(new TimeEvent.Period());
				if (year)
					MinecraftForge.EVENT_BUS.post(new TimeEvent.Year());
			}
	}
}
