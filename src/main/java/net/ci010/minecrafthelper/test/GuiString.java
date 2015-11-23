package net.ci010.minecrafthelper.test;

/**
 * @author ci010
 */
public class GuiString implements GuiComponent
{
	private String string;
	private int x, y;

	public GuiString(String string, int x, int y)
	{
		this.string = string;
		this.x = x;
		this.y = y;
	}

	@Override
	public void draw()
	{

	}
}
