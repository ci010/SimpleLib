package api.simplelib.remote.gui.plugins;

import api.simplelib.remote.gui.components.GuiComponent;

/**
 * @author ci010
 */
public interface Plugin
{
	void plugin(GuiComponent component);

	void dispose();
}
