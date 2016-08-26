package api.simplelib.sync;

import api.simplelib.seril.ICompoundSerializable;
import api.simplelib.vars.VarNotify;

/**
 * @author ci010
 */
public interface VarSync<T> extends VarNotify<T>, ICompoundSerializable
{
	UpdateMode getUpdateMode();
}
