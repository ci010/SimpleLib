package net.simplelib.time;

import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.Event;

/**
 * @author ci010
 */
public class TimeEvent extends Event
{
	protected TimeController.Period period;
	protected World world;

	protected TimeEvent(World world, TimeController.Period period)
	{
		this.world = world;
		this.period = period;
	}

	public static class Init extends TimeEvent
	{
		private TimeController controller;

		protected Init(World world, TimeController controller, TimeController.Period period)
		{
			super(world, period);
			this.controller = controller;
		}

		public TimeController getController()
		{
			return this.controller;
		}
	}

	public static class NewDay extends TimeEvent
	{
		public NewDay(World world, TimeController.Period period)
		{
			super(world, period);
		}
	}

	public static class NewPeriod extends TimeEvent
	{
		protected NewPeriod(World world, TimeController.Period period)
		{
			super(world, period);
		}
	}

	public static class NewYear extends TimeEvent
	{
		public final int newYear;

		protected NewYear(World world, TimeController.Period period, int year)
		{
			super(world, period);
			this.newYear = year;
		}
	}
}
