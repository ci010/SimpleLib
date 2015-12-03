package net.ci010.minecrafthelper.gui;

import net.minecraft.client.gui.Gui;

/**
 * @author ci010
 */
public abstract class GuiComponent extends Gui implements Drawable
{
	protected int x, y;

	public Type type()
	{
		return Type.back;
	}

	public enum Type
	{
		front, back
	}

	public int getX()
	{
		return x;
	}

	public int getY()
	{
		return y;
	}

	public abstract int getWidth();

	public abstract int getHeight();

	/**
	 * button: 1-left, 2-middle, 3-right
	 */
	interface MouseListener
	{
		void actionPerform(int x, int y, int button);
	}

	public interface MouseClick extends MouseListener {}

	public interface MouseRelease extends MouseListener {}

}
