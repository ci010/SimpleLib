package net.simplelib.interactive.process;

import net.simplelib.common.UpdateSafe;

/**
 * @author ci010
 */
public abstract class VarInc extends VarInteger implements UpdateSafe
{
	public VarInc(int i)
	{
		super(i);
	}

	@Override
	public void update()
	{
		this.setData(this.get() + 1);
	}
}
