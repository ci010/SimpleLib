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
		Boolean last = properties.getCache("last-shake");
		if (last == null)
		{
			properties.putCache("last-shake", Boolean.FALSE);
			last = Boolean.FALSE;
		}
		if (last)
			GlStateManager.translate(1, 0, 0);
		else
			GlStateManager.translate(-1, 0, 0);
	}
}
