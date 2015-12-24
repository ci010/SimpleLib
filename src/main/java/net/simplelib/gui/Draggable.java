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
//	private boolean clicking;
//
//	public boolean isClicking()
//	{
//		return clicking;
//	}

	public Draggable(GuiComponent gui)
	{
		this.gui = gui;
	}

//	@Override
//	public void onClick(int mouseX, int mouseY, int button)
//	{
//		if (button == 1)
//		{
//			adjustX = mouseX - gui.getX();
//			adjustY = mouseY - gui.getY();
//			this.clicking = true;
//		}
//	}
//
//	@Override
//	public void onRelease(int mouseX, int mouseY, int button)
//	{
//		if (button == 1)
//			this.clicking = false;
//	}
//
//	@Override
//	public void onMove(int mouseX, int mouseY)
//	{
//		if (clicking) gui.setPos(mouseX - adjustX, mouseY - adjustY);
//	}

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
