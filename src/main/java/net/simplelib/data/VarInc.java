package net.simplelib.data;

import net.simplelib.abstracts.UpdateSafe;

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
		this.setData(this.getData() + 1);
	}
}
