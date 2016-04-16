package test.api.component.entity;

import api.simplelib.Var;
import com.google.common.base.Function;

import java.util.UUID;

/**
 * @author ci010
 */
public interface IAttribute<T>
{
	String getId();

	Class<T> getType();

	T getDefaultValue();

	boolean shouldWatch();

	interface Instance<T> extends Var<T>
	{
		IAttribute<T> parent();

		void add(UUID uuid, Function<T, T> access);

		Function<T, T> remove(UUID uuid);
	}
}
