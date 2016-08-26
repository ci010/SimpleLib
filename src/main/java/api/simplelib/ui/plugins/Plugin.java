package api.simplelib.ui.plugins;

import api.simplelib.ui.elements.Element;

/**
 * @author ci010
 */
public interface Plugin
{
	void plugin(Element component);

	void dispose(Element element);
}
