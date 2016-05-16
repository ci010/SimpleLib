package api.simplelib.gui;

import api.simplelib.vars.Var;
import api.simplelib.vars.VarOption;
import net.minecraft.util.ResourceLocation;

import java.util.List;

/**
 * @author ci010
 */
public interface Properties
{
	<T> VarOption<T> property(Key<T> location);

	<T> Var<T> cache(String id);

	void clearCache();

	List<String> allCaches();

	List<Key> allProperties();

	interface Key<T>
	{
		ResourceLocation location();

		Class<T> type();
	}
}
