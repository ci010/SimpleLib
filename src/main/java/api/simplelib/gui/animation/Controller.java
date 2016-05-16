package api.simplelib.gui.animation;

import api.simplelib.gui.components.GuiComponent;

/**
 * The controller controls how you draw the gui. Used to implement animations,
 *
 * @author ci010
 */
public interface Controller
{
	Controller DEFAULT = new Controller()
	{
		@Override
		public void onLoad(GuiComponent component) {}

		@Override
		public void draw(GuiComponent component) {component.draw();}

		@Override
		public void onRemoved(GuiComponent component) {}

	};

	void onLoad(GuiComponent component);

	void draw(GuiComponent component);

	void onRemoved(GuiComponent component);
}
