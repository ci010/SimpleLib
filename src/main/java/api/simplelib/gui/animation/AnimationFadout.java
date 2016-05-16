package api.simplelib.gui.animation;

/**
 * @author ci010
 */
public class AnimationFadout extends ControlAlpha
{
	public static final AnimationFadout INSTANCE = new AnimationFadout();

	public AnimationFadout()
	{
		super(0, null);
	}

	@Override
	protected float getAlpha()
	{
		return this.current / time;
	}
}
