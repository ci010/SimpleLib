package api.simplelib.gui.node;

/**
 * @author ci010
 */
public class AnimationFadeOut extends AlphaNode
{
	public static final AnimationFadeOut INSTANCE = new AnimationFadeOut();

	@Override
	protected float getAlpha(float totalTime, float currentTime)
	{
		return (totalTime - currentTime) / totalTime;
	}
}
