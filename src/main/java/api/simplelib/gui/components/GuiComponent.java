package api.simplelib.gui.components;

import api.simplelib.Pipeline;
import api.simplelib.gui.DrawNode;
import api.simplelib.gui.animation.Controller;
import api.simplelib.gui.Properties;
import api.simplelib.gui.mouse.MouseProperty;
import net.minecraft.inventory.Container;

import java.util.List;

/**
 * @author ci010
 */
public class GuiComponent
{
	public static GuiComponent fromJson(String json)
	{
		return null;
	}

	protected int x, y, width, height;
	private MouseProperty mouse;
	private Controller controller;
	private Pipeline<DrawNode> drawPipe;
	private Properties properties;

	private GuiComponent parent;
	protected List<GuiComponent> children;

	public Properties getProperties()
	{
		return properties;
	}

	public Pipeline<DrawNode> getDrawPipe()
	{
		return drawPipe;
	}

//	/**
//	 * The type of the component. Only used for
//	 * {@link GuiContainer#drawGuiContainerBackgroundLayer(float, int, int)}
//	 * and {@link GuiContainer#drawGuiContainerForegroundLayer(int, int)}}
//	 */
//	public enum Type
//	{
//		front, back
//	}

	public boolean hasMouseListener()
	{
		return mouse != null;
	}

	/**
	 * @return The mouse listener of this component.
	 */
	public MouseProperty getMouseListener()
	{
		if (mouse == null)
			mouse = new MouseProperty();
		return mouse;
	}

	public GuiComponent setController(Controller controller)
	{
		if (this.controller != null)
			this.controller.onRemoved(this);
		this.controller = controller;
		controller.onLoad(this);
		return this;
	}

	public Controller getController()
	{
		if (parent != null)
			if (controller == null)
				return parent.getController();
			else
				return this.controller;
		else return Controller.DEFAULT;
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
		if (component.controller == Controller.DEFAULT)
			component.setController(this.controller);
		return this;
	}

	protected void setMouseListener(MouseProperty property)
	{
		this.mouse = property;
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

	public final void draw()
	{
		for (DrawNode drawNode : this.drawPipe)
			drawNode.draw(this.getX(), this.getY(), this.properties);
		if (children != null && !children.isEmpty())
			for (GuiComponent child : children)
				child.draw();
	}

	/**
	 * This method will be called when the player open the GuiContainer containing this component.
	 */
	public void onLoad(Container container)
	{
	}

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
	public int getWidth()
	{
		return this.width;
	}

	/**
	 * @return The height of the component.
	 */
	public int getHeight()
	{
		return this.height;
	}
}
