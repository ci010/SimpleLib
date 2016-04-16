package net.simplelib.gui;

/**
 * @author ci010
 */
public class AnimationFadeOut extends ControllerAlpha
{
	public AnimationFadeOut(float time, Controller controller)
	{
		super(time, controller);
	}

	@Override
	protected float getAlpha()
	{
		return -(1 / time) + 1;
	}

	@Override
	public void draw(Target target, int x, int y)
	{

	}
}
