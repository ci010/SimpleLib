package api.simplelib.remote.gui.node;

import api.simplelib.Pipeline;
import api.simplelib.remote.gui.Properties;
import api.simplelib.remote.gui.components.GuiComponent;

/**
 * @author ci010
 */
public abstract class AlphaNode implements DrawNode
{
	@Override
	public void draw(GuiComponent.Transform transform, Pipeline<DrawNode> pipeline, Properties properties)
	{
//		VarForward<Integer> totalTime = properties.property(ComponentAPI.PROP_FADE_TIME);
//		if (!totalTime.isPresent())
//		{
//
//		}
//		float time = totalTime.get(), current = 0;
//		Var<Float> currentTime = properties.getCache("currentTime");
//		if (currentTime.isEmpty())
//			current = currentTime.get();
//		if (++current >= time)
//		{
//			pipeline.remove(this);
//			return;
//		}
//		currentTime.set(current);
//		GlStateManager.enableBlend();
//		GlStateManager.enableAlpha();
//		GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_ONE_MINUS_SRC_ALPHA);
//		float alpha = getAlpha(time, current);
//		GlStateManager.color(alpha, alpha, alpha, alpha);
	}

	protected abstract float getAlpha(float totalTime, float currentTime);
}
