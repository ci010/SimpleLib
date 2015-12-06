package net.ci010.minecrafthelper.gui;

import net.minecraft.client.gui.GuiButton;

/**
 * @author ci010
 */
public class Draggable extends GuiButton// implements GuiComponent.MouseListener
{
	public Draggable(int buttonId, int x, int y, String buttonText)
	{
		super(buttonId, x, y, buttonText);
	}
}
