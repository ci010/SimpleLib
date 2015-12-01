package net.ci010.minecrafthelper.gui;

import net.minecraft.client.gui.Gui;

/**
 * @author ci010
 */
public abstract class GuiComponent extends Gui implements Drawable
{
	public Type type()
	{
		return Type.back;
	}

	public enum Type
	{
		front, back
	}
}
