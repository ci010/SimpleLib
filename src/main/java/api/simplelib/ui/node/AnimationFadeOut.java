package api.simplelib.ui.node;

import api.simplelib.ui.elements.Element;
import api.simplelib.vars.VarRef;

/**
 * @author ci010
 */
public class AnimationFadeOut extends AlphaNode
{
	public static final AnimationFadeOut INSTANCE = new AnimationFadeOut();

	@Override
	protected int getFadeTime(Element component)
	{
		VarRef<Number> var = component.getProperties().num("fade-out");
		if (var.isPresent())
			return var.get().intValue();
		else return 0;
	}

	@Override
	protected float getAlpha(float totalTime, float currentTime)
	{
		return (totalTime - currentTime) / totalTime;
	}
}
