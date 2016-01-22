package net.simplelib.time;

import net.minecraftforge.fml.common.eventhandler.Event;

import java.sql.Time;

/**
 * @author ci010
 */
public class TimeEvent extends Event
{
	public static class RegPeriod extends TimeEvent
	{

	}

	public static class Day extends TimeEvent
	{

	}

	public static class Period extends TimeEvent
	{}

	public static class Year extends TimeEvent
	{

	}
}
