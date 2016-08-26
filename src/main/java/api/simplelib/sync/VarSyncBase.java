package api.simplelib.sync;

import api.simplelib.vars.VarNotifyBase;

/**
 * @author ci010
 */
public abstract class VarSyncBase<T> extends VarNotifyBase<T> implements VarSync<T>
{
	protected String name;
	protected UpdateMode mode;

	public VarSyncBase(String name, UpdateMode frequency)
	{
		this.name = name;
		this.mode = frequency;
	}

	@Override
	public UpdateMode getUpdateMode()
	{
		return mode;
	}

	@Override
	public String toString()
	{
		return this.data.toString();
	}
}
