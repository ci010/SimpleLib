package net.simplelib.interactive.process;

import net.simplelib.common.UpdateSafe;

/**
 * @author ci010
 */
public abstract class VarDec extends VarInteger implements UpdateSafe
{
	public VarDec(int i)
	{
		super(i);
	}

	@Override
	public void update()
	{
		this.setData(this.get() - 1);
	}
}
