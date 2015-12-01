package net.ci010.minecrafthelper.machine;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;

/**
 * @author ci010
 */
public abstract class GuiContainerWrap extends GuiContainer
{
	public GuiContainerWrap(Container inventorySlotsIn)
	{
		super(inventorySlotsIn);
	}
}
