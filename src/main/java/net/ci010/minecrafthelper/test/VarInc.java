package net.ci010.minecrafthelper.test;

/**
 * @author ci010
 */
public abstract class VarInc extends Process.Var<Integer> implements Process
{
	@Override
	public void update()
	{
		this.setData(this.getData() + 1);
	}
}
