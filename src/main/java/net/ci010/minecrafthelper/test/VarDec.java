package net.ci010.minecrafthelper.test;

/**
 * @author ci010
 */
public abstract class VarDec extends VarInteger implements MachineProcess
{
	@Override
	public void update()
	{
		this.setData(this.getData() - 1);
	}
}
