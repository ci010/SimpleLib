package api.simplelib.gui.node;

import api.simplelib.Pipeline;
import api.simplelib.gui.Properties;
import net.minecraft.client.renderer.GlStateManager;

/**
 * @author ci010
 */
public class AnimationShake implements DrawNode
{
	@Override
	public void draw(int x, int y, Pipeline<DrawNode> pipeline, Properties properties)
	{
		GlStateManager.translate(1, 0, 0);
	}
}
