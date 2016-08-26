package net.simplelib.sync;

import api.simplelib.sync.UpdateMode;
import api.simplelib.sync.VarSync;
import api.simplelib.sync.AttributeFactory;
import api.simplelib.vars.Var;
import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;

import javax.annotation.Nonnull;
import java.util.Map;

/**
 * @author ci010
 */
public class CommonVarFactory implements AttributeFactory
{
	public Map<String, VarSync<?>> constantly = Maps.newHashMap(), lazy = Maps.newHashMap();

	private <T> Var<T> newVar(String name, UpdateMode mode, T v)
	{
		Preconditions.checkNotNull(v, "The default value cannot be null!");
		Preconditions.checkNotNull(name, "The variable's name cannot be null!");
		if (mode == null) mode = UpdateMode.LAZY;
		VarSync<T> var = produceVar(name, mode, v);
		if (mode == UpdateMode.CONSTANTLY) constantly.put(name, var);
		else lazy.put(name, var);
		return var;
	}

	protected <T> VarSync<T> produceVar(String name, UpdateMode mode, T v)
	{
		return new VarSyncPrimitive<>(name, v, mode);
	}


	@Override
	public <T extends Number> Var<T> newNumber(@Nonnull String name, @Nonnull T v, UpdateMode mode)
	{
		return newVar(name, mode, v);
	}

	@Override
	public <T extends Number> Var<T> newNumber(@Nonnull String name, @Nonnull T v, T min, T max, UpdateMode mode)
	{
		Preconditions.checkNotNull(v, "The default value cannot be null!");
		Preconditions.checkNotNull(name, "The variable's name cannot be null!");
		if (mode == null) mode = UpdateMode.LAZY;
		VarSyncPrimitive.Ranged<T> ranged = new VarSyncPrimitive.Ranged<>(name, v, min, max, mode);
		if (mode == UpdateMode.CONSTANTLY) constantly.put(name, ranged);
		else lazy.put(name, ranged);
		return ranged;
	}

	@Override
	public Var<Boolean> newBoolean(@Nonnull String name, boolean b, UpdateMode mode)
	{
		return newVar(name, mode, b);
	}

	@Override
	public Var<String> newString(@Nonnull String name, @Nonnull String s, UpdateMode mode)
	{
		return newVar(name, mode, s);
	}

	@Override
	public <T extends Enum<T>> Var<T> newEnum(@Nonnull String name, @Nonnull T e, UpdateMode mode)
	{
		Preconditions.checkNotNull(e, "The default value cannot be null!");
		Preconditions.checkNotNull(name, "The variable's name cannot be null!");
		if (mode == null) mode = UpdateMode.LAZY;
		VarSyncPrimitive.VEnum<T> v = new VarSyncPrimitive.VEnum<>(name, e, mode);
		if (mode == UpdateMode.CONSTANTLY) constantly.put(name, v);
		else lazy.put(name, v);
		return v;
	}
}
