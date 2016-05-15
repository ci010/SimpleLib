package api.simplelib.vars;

import com.google.common.base.Supplier;

/**
 * @author ci010
 */
public interface Var<T> extends Supplier<T>
{
	void set(T value);
}
