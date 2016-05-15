package api.simplelib.vars;

/**
 * @author ci010
 */
public abstract class VarSyncBase<T> extends VarNotifyBase<T> implements VarSync<T>
{
	@Override
	public String toString()
	{
		return this.data.toString();
	}
}
