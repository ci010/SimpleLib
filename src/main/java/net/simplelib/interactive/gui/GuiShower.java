package net.simplelib.interactive.gui;

import net.simplelib.gui.GuiProvider;
import api.simplelib.interactive.Interactive;
import net.simplelib.network.GuiHandler;

/**
 * This interface indicates that this contains Gui.
 *
 * @author ci010
 */

public interface GuiShower extends Interactive, GuiProvider
{
	interface Custom extends Interactive, GuiHandler.ContainerProvider
	{}
}
