package api.simplelib.gui;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.inventory.GuiContainer;

import java.util.List;

/**
 * @author ci010
 */
public abstract class GuiComponent extends Gui implements Drawable
{
	protected int x, y;
	protected float scale;
	private MouseProperty property;
	protected List<GuiComponent> children;
	private Controller controller;
	private GuiComponent parent;

	interface Base
	{
		int x();

		int y();

		int width();

		int height();

		float scale();
	}

	/**
	 * @return The type of this component
	 */
	public Type type()
	{
		return Type.back;
	}


	/**
	 * The type of the component. Only used for
	 * {@link GuiContainer#drawGuiContainerBackgroundLayer(float, int, int)}
	 * and {@link GuiContainer#drawGuiContainerForegroundLayer(int, int)}}
	 */
	public enum Type
	{
		front, back
	}

	public boolean hasMouseListener()
	{
		return property != null;
	}

	/**
	 * @return The mouse listener of this component.
	 */
	public MouseProperty getMouseListener()
	{
		if (property == null)
			property = new MouseProperty();
		return property;
	}

	public GuiComponent setController(Controller controller)
	{
		this.controller = controller;
		return this;
	}

	public Controller getController()
	{
		if (controller == null)
			return parent.getController();
		return this.controller;
	}

	/**
	 * This is just a really simple function which indicates the order of draw of the gui.
	 * <p>The position of component on parameter will relate to this component's position.</p>
	 * <p>If this position is (50,50) and the position of component on parameter is (10,10). The actual position of
	 * the component on parameter is (60,60) if this component has no parent.</p>
	 *
	 * @param component The component may overlap this component.
	 * @return this
	 */
	public GuiComponent add(GuiComponent component)
	{
		this.children.add(component);
		component.parent = this;
		return this;
	}

	protected void setMouseListener(MouseProperty property)
	{
		this.property = property;
	}

	/**
	 * Set the relative position of this component.
	 *
	 * @param x The position x of the component.
	 * @param y The position y of the component.
	 * @return this
	 */
	public GuiComponent setPos(int x, int y)
	{
		this.x = x;
		this.y = y;
		return this;
	}

	public GuiComponent setScale(float scale)
	{
		this.scale = scale;
		return this;
	}

	/**
	 * This method will be called when the player open the GuiContainer containing this component.
	 */
	public void initGui()
	{}

	/**
	 * @return The x position of the component.
	 */
	public int getX()
	{
		return x;
	}

	/**
	 * @return The y position of the component.
	 */
	public int getY()
	{
		return y;
	}

	/**
	 * @return The width of the component.
	 */
	public abstract int getWidth();

	/**
	 * @return The height of the component.
	 */
	public abstract int getHeight();
}
