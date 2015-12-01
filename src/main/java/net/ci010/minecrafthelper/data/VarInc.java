package net.ci010.minecrafthelper.data;

import net.ci010.minecrafthelper.abstracts.UpdateSafe;

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
