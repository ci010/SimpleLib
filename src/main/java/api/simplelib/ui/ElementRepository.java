package api.simplelib.ui;

import api.simplelib.Pipeline;
import api.simplelib.registry.ModProxy;
import api.simplelib.ui.node.DrawNode;
import net.minecraft.util.ResourceLocation;

/**
 * @author ci010
 */
public interface ElementRepository
{
	@ModProxy.Inject
	ElementRepository repository = null;

	DrawNode fetchNode(ResourceLocation location);

	ResourceLocation fromNode(DrawNode node);

//	<T> Properties.Key<T> fetchKey(ResourceLocation location, Class<T> type);
//
//	<T> Properties.Key<T> registerKey(ResourceLocation location, Class<T> type, IJsonSerializer<T> serializer);

	Properties newProperty();

	Pipeline<DrawNode> newDrawPipe();
}
