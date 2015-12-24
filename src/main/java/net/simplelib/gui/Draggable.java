package net.simplelib.gui;

/**
 * Implement the basic drag function.
 * <p/>
 * The component will change its position according to the mouse's dragging.
 *
 * @author ci010
 */
public class Draggable implements MouseProperty.Drag
{
	private GuiComponent gui;
	private int adjustX, adjustY;

	public Draggable(GuiComponent gui)
	{
		this.gui = gui;
	}

	@Override
	public void onDrag(int mouseX, int mouseY, int button, float second)
	{
		if (second <= 0.1)
		{
			adjustX = mouseX - gui.getX();
			adjustY = mouseY - gui.getY();
		}
		gui.setPos(mouseX - adjustX, mouseY - adjustY);
	}
}
