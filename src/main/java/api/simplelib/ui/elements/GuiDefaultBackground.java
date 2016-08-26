package api.simplelib.ui.elements;

/**
 * @author ci010
 */
public class GuiDefaultBackground extends Element
{
	public GuiDefaultBackground(int x, int y, int width, int height)
	{
//		this.getDrawPipe().addLast(ElementAPI.DRAW_BACKGROUND);
//		this.getProperties().put(ElementAPI.PROP_BACK_SIZE, Pair.of(width, height));
//		super(type, getName);
		this.transform.setPos(x, y);
	}
}
