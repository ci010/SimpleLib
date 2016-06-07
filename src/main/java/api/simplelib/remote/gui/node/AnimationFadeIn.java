package api.simplelib.remote.gui.node;

/**
 * @author ci010
 */
public class AnimationFadeIn extends AlphaNode
{
	public static final AnimationFadeIn INSTANCE = new AnimationFadeIn();

	private AnimationFadeIn() {}

	@Override
	protected float getAlpha(float totalTime, float currentTime)
	{
		return currentTime / totalTime;
	}
}
