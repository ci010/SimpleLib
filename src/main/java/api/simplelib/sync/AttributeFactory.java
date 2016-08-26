package api.simplelib.sync;

import api.simplelib.capabilities.CapabilityBuilder;
import api.simplelib.vars.Var;

import javax.annotation.Nonnull;

/**
 * @author ci010
 */
@CapabilityBuilder(AttributeView.class)
public interface AttributeFactory
{
	/**
	 * Create a new variable for number.
	 *
	 * @param <T>
	 * @param name The name of the variable. This should be identical as it will be used as the name of the nbt tag.
	 * @param v
	 * @return
	 */
	<T extends Number> Var<T> newNumber(@Nonnull String name, @Nonnull T v, UpdateMode mode);

	<T extends Number> Var<T> newNumber(@Nonnull String name, @Nonnull T v, T min, T max, UpdateMode mode);

	Var<Boolean> newBoolean(@Nonnull String name, boolean b, UpdateMode mode);

	Var<String> newString(@Nonnull String name, @Nonnull String s, UpdateMode mode);

	<T extends Enum<T>> Var<T> newEnum(@Nonnull String name, @Nonnull T e, UpdateMode mode);
}
