package api.simplelib.vars;

import api.simplelib.Callback;

/**
 * @author ci010
 */
public interface VarOption<T> extends Var<T>, Callback<T>
{
	boolean isPresent();

	void present(VarNotify<T> var);
}
