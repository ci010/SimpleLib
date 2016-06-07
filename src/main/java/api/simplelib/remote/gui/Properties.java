package api.simplelib.remote.gui;

import api.simplelib.vars.VarSync;
import com.google.common.base.Optional;
import net.minecraft.util.ResourceLocation;

import java.util.Set;

/**
 * @author ci010
 */
public interface Properties
{
	/**
	 * Using a sync var for this key as the property.
	 * <p>This value will be auto synced.</p>
	 *
	 * @param location The key
	 * @param sync     Sync reference
	 * @param <T>      Type
	 */
	<T> void using(Key<T> location, VarSync<T> sync);

	/**
	 * Put a value as a property.
	 * <p>Notice that this won't be sync, and this value is immutable to client.</p>
	 *
	 * @param location
	 * @param value
	 * @param <T>
	 */
	<T> void put(Key<T> location, T value);

	/**
	 * Get the property value at certain location.
	 *
	 * @param location
	 * @param <T>
	 * @return Property value
	 */
	<T> Optional<T> get(Key<T> location);

	Set<Key> allProperties();

	<T> T getCache(String id);

	void putCache(String id, Object cache);

	void clearCache();

	Set<String> allCaches();

	Set<VarSync> usingResource();

	interface Key<T>
	{
		ResourceLocation location();

		Class<T> type();
	}
}
