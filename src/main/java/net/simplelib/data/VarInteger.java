package net.simplelib.data;

/**
 * @author ci010
 */
public class VarInteger extends Var<Integer>
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
		super.setData(data);
	}
}
