package api.simplelib.vars;

import java.util.Map;

/**
 * @author ci010
 */
public interface VarRef<T> extends Var<T>
{
	boolean isPresent();

	String getName();

	T getIfPresent(T fallback);

	T setIfAbsent(T value);

	interface Delegate<T> extends VarRef<T>
	{
		void setDelegate(Var<? extends T> var);

		Var<T> getDelegate();
	}

	interface Mapping<T> extends VarRef<T>
	{
		void setMap(Map<String, T> map);

		Map<String, T> getMap();
	}
}
