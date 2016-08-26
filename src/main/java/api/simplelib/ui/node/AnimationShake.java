package api.simplelib.ui.node;

import api.simplelib.ui.elements.Element;
import api.simplelib.vars.VarRef;
import net.minecraft.client.renderer.GlStateManager;

/**
 * @author ci010
 */
public class AnimationShake implements DrawNode
{
	@Override
	public boolean apply(Element component)
	{
		VarRef<Number> shake = component.getProperties().num("shake");
		int shakeTime = 0;
		if (shake.isPresent())
			shakeTime = shake.get().intValue();
		else
			return true;
		VarRef<Boolean> cache = component.getProperties().cache("last-shake");
		boolean lastShake = false;
		if (cache.isPresent())
			lastShake = cache.get();
		else
			cache.set(false);
		if (lastShake)
			GlStateManager.translate(1, 0, 0);
		else
			GlStateManager.translate(-1, 0, 0);
		return false;
	}
}
