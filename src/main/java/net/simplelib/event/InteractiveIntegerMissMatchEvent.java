package net.simplelib.event;

import net.minecraftforge.fml.common.eventhandler.Event;
import net.simplelib.data.VarInteger;

/**
 * @author ci010
 */
public class InteractiveIntegerMissMatchEvent extends Event
{
	private String name;
	private int[] data;
	private VarInteger[] vars;

	public InteractiveIntegerMissMatchEvent(String name, int[] data, VarInteger[]
			varIntegers)
	{
		this.data = data;
		this.name = name;
		this.vars = varIntegers;
	}

	public int[] getNBTData()
	{
		return data;
	}

	public String getName()
	{
		return name;
	}

	public VarInteger[] getVars()
	{
		return vars;
	}
}
