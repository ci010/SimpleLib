package api.simplelib.gui;

/**
 * Basic implementation of showing tooltip while mouse hovers.
 *
 * @author ci010
 */
public abstract class HoverTooltip implements MouseProperty.Hover
{
	private GuiBorderTexts texts;

	@Override
	public void onHover(int mouseX, int mouseY)
	{
		if (texts == null)
			texts = getTextLines();
		texts.setPos(mouseX, mouseY);
		texts.draw();
	}

	@Override
	public float delay()
	{
		return 1.5f;
	}

	public abstract GuiBorderTexts getTextLines();
}
