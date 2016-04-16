package net.simplelib.gui;

/**
 * @author ci010
 */
public interface Controller
{
	void draw(Target target, int x, int y);

	interface Target extends Drawer
	{
		void setController(Controller controller);
	}
}
