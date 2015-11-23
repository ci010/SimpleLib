package net.ci010.minecrafthelper.test;

import java.lang.reflect.Field;

/**
 * @author ci010
 */
public abstract class VarDec extends Process.Var<Integer> implements Process
{
	@Override
	public void update()
	{
		this.setData(this.getData() - 1);
	}
}
