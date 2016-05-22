package api.simplelib.gui.node;

import api.simplelib.Pipeline;
import api.simplelib.gui.Properties;
import net.minecraft.client.renderer.GlStateManager;

/**
 * @author ci010
 */
public interface DrawNode
{
	void draw(int x, int y, Pipeline<DrawNode> pipeline, Properties properties);
}
