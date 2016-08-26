package api.simplelib.seril;

import api.simplelib.utils.DebugLogger;
import api.simplelib.utils.TypeUtils;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Collection;
import java.util.Map;

/**
 * @author ci010
 */
@ParametersAreNonnullByDefault
public class SerialRepo
{
	private static Table<ResourceLocation, Class, SerialKey.Json> json = HashBasedTable.create();
	private static Table<ResourceLocation, Class, SerialKey.NBT> nbt = HashBasedTable.create();

	public static final ResourceLocation DEFAULT = new ResourceLocation("serialization:default");

	public static <T> void registerDefault(Class<T> type, INBTSerializer<T> serializer)
	{
		register(DEFAULT, type, serializer);
	}

	public static <T> void registerDefault(Class<T> type, IJsonSerializer<T> serializer)
	{
		register(DEFAULT, type, serializer);
	}

	@Nullable
	public static <T> SerialKey.Json<T> fetchJsonDefualt(Class<T> type)
	{
		return fetchJson(DEFAULT, type);
	}

	@Nullable
	public static <T> SerialKey.NBT<T> fetchNBTDefualt(Class<T> type)
	{
		return fetchNBT(DEFAULT, type);
	}

	@Nonnull
	public static <T> Map<String, SerialKey.Json<T>> fetchAllJson(Class<T> type)
	{
		return TypeUtils.cast(json.column(type));
	}

	@Nonnull
	public static <T> Collection<SerialKey.NBT<T>> fetchAllNBT(Class<T> type)
	{
		return TypeUtils.cast(nbt.column(type));
	}

	@Nullable
	public static <T> SerialKey.NBT<T> fetchNBT(ResourceLocation location, Class<T> type)
	{
		return nbt.get(location, type);
	}

	@Nullable
	public static <T> SerialKey.Json<T> fetchJson(ResourceLocation location, Class<T> type)
	{
		return json.get(location, type);
	}

	public static <T> void register(ResourceLocation location, Class<T> type, INBTSerializer<T> serializer)
	{
		SerialKey.NBT<T> tnbt = fetchNBT(DEFAULT, type);
		if (tnbt != null)
			nbt.put(location, type, new SerialKey.NBT<>(location, type, serializer));
		else
			DebugLogger.fatal("Cannot register {} for {} as a nbt serializer since it's already registered!",
					serializer, type);
	}

	public static <T> void register(ResourceLocation location, Class<T> type, IJsonSerializer<T> serializer)
	{
		SerialKey.Json<T> tJson = fetchJson(DEFAULT, type);
		if (tJson != null)
			json.put(location, type, new SerialKey.Json<>(location, type, serializer));
		else
			DebugLogger.fatal("Cannot register {} for {} as a json serializer since it's already registered!",
					serializer, type);
	}
}
