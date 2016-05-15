package api.simplelib.vars;

import api.simplelib.utils.Callback;

/**
 * @author ci010
 */
public interface VarNotify<T> extends Var<T>, Callback.Container<VarNotify<T>>
{
}
