package net.simplelib.interactive.process;

import api.simplelib.VarBase;

/**
 * @author ci010
 */
public class VarInteger extends VarBase<Integer>
{
	public VarInteger(int value)
	{//TODO fix this
		this.setData(value);
	}

	protected boolean dirty;

	public boolean isDirty()
	{
		return this.dirty;
	}

	public void setData(int data)
	{
		this.dirty = true;
		super.set(data);
	}
}
