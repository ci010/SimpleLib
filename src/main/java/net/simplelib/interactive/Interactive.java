package net.simplelib.interactive;

import net.simplelib.gui.GuiProvider;

/**
 * @author ci010
 */
public interface Interactive extends GuiProvider
{
	String getId();

	void provideInventory(InventoryManager collection);

	void provideProcess(ProcessHandler handler);
}
