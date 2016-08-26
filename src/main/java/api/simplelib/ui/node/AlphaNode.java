package api.simplelib.ui.node;

import api.simplelib.ui.elements.Element;
import api.simplelib.vars.VarRef;
import net.minecraft.client.renderer.GlStateManager;

/**
 * @author ci010
 */
public abstract class AlphaNode implements DrawNode
{
	@Override
	public boolean apply(Element component)
	{
		int fadeTime = getFadeTime(component), current = 0;
		VarRef<Integer> currentTime = component.getProperties().cache("currentTime");
		if (currentTime.isPresent())
			current = currentTime.get();
		else
			currentTime.set(0);
		if (++current >= fadeTime)
			return true;
		currentTime.set(current);
		GlStateManager.enableBlend();
		GlStateManager.enableAlpha();
		GlStateManager.blendFunc(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
		float alpha = getAlpha(fadeTime, current);
		GlStateManager.color(alpha, alpha, alpha, alpha);
		return false;
	}

	protected abstract int getFadeTime(Element component);

	protected abstract float getAlpha(float totalTime, float currentTime);
}
