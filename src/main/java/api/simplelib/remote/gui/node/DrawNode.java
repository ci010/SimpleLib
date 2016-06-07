package api.simplelib.remote.gui.node;

import api.simplelib.Pipeline;
import api.simplelib.remote.gui.Properties;
import api.simplelib.remote.gui.components.GuiComponent;

/**
 * @author ci010
 */
public interface DrawNode
{
	void draw(GuiComponent.Transform transform, Pipeline<DrawNode> pipeline, Properties properties);
}
