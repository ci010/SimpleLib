package api.simplelib.gui.plugins;

import api.simplelib.gui.components.GuiComponent;

/**
 * @author ci010
 */
public interface Plugin
{
	void plugin(GuiComponent component);

	void dispose();
}
