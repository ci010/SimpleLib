package api.simplelib.gui;

import api.simplelib.gui.components.GuiComponent;

import java.util.List;

/**
 * @author ci010
 */
public interface ComponentProvider
{
	void provideComponents(List<GuiComponent> components);
}
