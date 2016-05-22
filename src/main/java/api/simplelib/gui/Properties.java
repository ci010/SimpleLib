package api.simplelib.gui;

import api.simplelib.vars.VarForward;
import net.minecraft.util.ResourceLocation;

import java.util.List;
import java.util.Set;

/**
 * @author ci010
 */
public interface Properties
{
	<T> VarForward<T> property(Key<T> location);

	<T> T getCache(String id);

	void putCache(String id, Object cache);

	void clearCache();

	Set<String> allCaches();

	Set<Key> allProperties();

	interface Key<T>
	{
		ResourceLocation location();

		Class<T> type();
	}
}
