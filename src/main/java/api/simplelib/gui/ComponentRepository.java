package api.simplelib.gui;

import api.simplelib.Pipeline;
import api.simplelib.gui.node.DrawNode;
import net.minecraft.util.ResourceLocation;

/**
 * @author ci010
 */
public interface ComponentRepository
{
	DrawNode fetchNode(ResourceLocation location);

	<T> Properties.Key<T> fetchKey(DrawNode node, Class<T> type);

	<T> Properties.Key<T> fetchKey(ResourceLocation location, Class<T> type);

	Properties newProperty();

	Pipeline<DrawNode> newDrawPipe();
}
