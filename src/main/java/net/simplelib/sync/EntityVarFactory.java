package net.simplelib.sync;

import api.simplelib.seril.DataSerializerView;
import api.simplelib.sync.UpdateMode;
import api.simplelib.sync.VarSync;
import api.simplelib.utils.TypeUtils;
import api.simplelib.vars.Var;
import com.google.common.base.Preconditions;
import net.minecraft.entity.Entity;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializer;
import net.minecraft.network.datasync.EntityDataManager;

import javax.annotation.Nonnull;

/**
 * @author ci010
 */
public class EntityVarFactory extends CommonVarFactory
{
	private Entity entity;

	public EntityVarFactory(Entity entity)
	{
		this.entity = entity;
	}

	protected <T> DataParameter<T> registerDataWatcher(T o)
	{
		DataSerializer<T> byClass = TypeUtils.cast(DataSerializerView.getByClass(o.getClass()));
		DataParameter<T> key = EntityDataManager.createKey(entity.getClass(), byClass);
		entity.getDataManager().register(key, o);
		return key;
	}

	@Override
	public <T extends Number> Var<T> newNumber(@Nonnull String name, @Nonnull T v, T min, T max, UpdateMode mode)
	{
		Preconditions.checkNotNull(v, "The default value cannot be null!");
		Preconditions.checkNotNull(name, "The variable's name cannot be null!");
		if (mode == null) mode = UpdateMode.LAZY;
		VarSync<T> varSync = mode == UpdateMode.LAZY ? new VarSyncPrimitive.Ranged<>(name, v, max, min, mode) :
				new VarWatching.Ranged<>(this.registerDataWatcher(v), entity.getDataManager(), name, v, min, max);
		if (mode == UpdateMode.CONSTANTLY) constantly.put(name, varSync);
		else lazy.put(name, varSync);
		return varSync;
	}

	@Override
	protected <T> VarSync<T> produceVar(String name, UpdateMode mode, T v)
	{
		if (mode == UpdateMode.LAZY)
			return super.produceVar(name, mode, v);
		return new VarWatching<>(registerDataWatcher(v), entity.getDataManager(), name, v);
	}

	@Override
	public <T extends Enum<T>> Var<T> newEnum(@Nonnull String name, @Nonnull T e, UpdateMode mode)
	{
		Preconditions.checkNotNull(e, "The default value cannot be null!");
		Preconditions.checkNotNull(name, "The variable's name cannot be null!");
		if (mode == null) mode = UpdateMode.LAZY;
		VarSync<T> v = mode == UpdateMode.LAZY ? new VarSyncPrimitive.VEnum<>(name, e, mode) :
				new VarWatching.VarWatchingEnum<>(entity, e, name);
		if (mode == UpdateMode.CONSTANTLY) constantly.put(name, v);
		else lazy.put(name, v);
		return v;
	}
}
