package net.simplelib.gui;

/**
 * @author ci010
 */
public class AnimationFadeIn extends ControllerAlpha
{
	public AnimationFadeIn(float time, Controller controller)
	{
		super(time, controller);
	}

	@Override
	protected float getAlpha()
	{
		return current / time;
	}

	@Override
	public void draw(Target target, int x, int y)
	{

	}
}
