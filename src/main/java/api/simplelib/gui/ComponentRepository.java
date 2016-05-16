package api.simplelib.gui;

import api.simplelib.gui.animation.Controller;
import net.minecraft.util.ResourceLocation;

/**
 * @author ci010
 */
public interface ComponentRepository
{
	DrawNode fetchDrawer(ResourceLocation location);

	Controller fetchController(ResourceLocation location);

	<T> Properties.Key<T> fetchKey(DrawNode node, Class<T> type);

	<T> Properties.Key<T> fetchKey(Controller controller, Class<T> type);

	<T> Properties.Key<T> fetchKey(ResourceLocation location, Class<T> type);
}
