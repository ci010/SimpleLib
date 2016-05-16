package api.simplelib.gui.components;

import api.simplelib.gui.mouse.MouseProperty;

/**
 * @author ci010
 */
public class GuiButtonMC extends GuiComponent//TODO finish
{
	private int width, height;
	private boolean isHovered;

	public void initGui()
	{
		this.getMouseListener().addListener(new MouseProperty.MouseClick()
		{
			@Override
			public void onClick(int mouseX, int mouseY, int button, boolean inRange)
			{
				if (inRange)
				{

				}
			}
		}).addListener(new MouseProperty.Hover()
		{
			@Override
			public void onHover(int mouseX, int mouseY)
			{

			}

			@Override
			public float delay()
			{
				return 0;
			}
		});
	}

	@Override
	public int getWidth()
	{
		return width;
	}

	@Override
	public int getHeight()
	{
		return height;
	}
}
