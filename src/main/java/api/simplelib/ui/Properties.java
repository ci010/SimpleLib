package api.simplelib.ui;

import api.simplelib.seril.SerialKey;
import api.simplelib.vars.VarArrayRef;
import api.simplelib.vars.VarRef;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Map;

/**
 * @author ci010
 */
public interface Properties
{
	@Nonnull
	VarRef<ResourceLocation> loc(@Nonnull String key);

	@Nonnull
	VarRef<Boolean> bool(@Nonnull String key);

	@Nonnull
	VarRef<String> str(@Nonnull String key);

	@Nonnull
	VarRef<Number> num(@Nonnull String key);

	@Nonnull
	VarArrayRef<Object> arr(@Nonnull String key);

	@Nonnull
	<T extends Enum<T>> VarRef<T> enm(@Nonnull Class<T> type, @Nonnull String key);

	@Nonnull
	<T> VarRef<T> var(@Nonnull ResourceLocation location, @Nonnull SerialKey.Json<T> key);

	@Nonnull
	<T> VarRef<T> cache(@Nonnull String name);

	ImmutableSet<String> allConstants();

	ImmutableSet<ResourceLocation> allVariables();
}
