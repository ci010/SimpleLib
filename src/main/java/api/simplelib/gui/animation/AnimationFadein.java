package api.simplelib.gui.animation;

/**
 * @author ci010
 */
public class AnimationFadein extends ControlAlpha
{
	public static final AnimationFadein INSTANCE = new AnimationFadein();

	private AnimationFadein()
	{
		super(0, null);
	}

	@Override
	protected float getAlpha()
	{
		return 1.01f / current;
	}
}
