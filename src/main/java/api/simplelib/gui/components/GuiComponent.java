package api.simplelib.gui.components;

import api.simplelib.Pipeline;
import api.simplelib.gui.ComponentAPI;
import api.simplelib.gui.node.DrawNode;
import api.simplelib.gui.Properties;
import api.simplelib.gui.plugins.Plugin;
import com.google.common.collect.Lists;
import net.minecraft.inventory.Container;

import java.util.List;

/**
 * @author ci010
 */
public class GuiComponent
{
	protected int x, y, width, height;
	private Pipeline<DrawNode> drawPipe;
	private Properties properties = ComponentAPI.repository.newProperty();
	private List<Plugin> plugins;

	private GuiComponent parent;
	protected List<GuiComponent> children;

	public Properties getProperties()
	{
		return properties;
	}

	public GuiComponent getParent()
	{
		return parent;
	}

	protected List<Plugin> getPlugins()
	{
		return plugins;
	}

	protected Pipeline<DrawNode> getDrawPipe()
	{
		if (drawPipe == null)
			drawPipe = ComponentAPI.repository.newDrawPipe();
		return drawPipe;
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

	public void applyPlugin(Plugin plugin)
	{
		if (plugins != null)
			plugins = Lists.newArrayList();
		plugin.plugin(this);
		this.plugins.add(plugin);
	}

	public void disposePlugin(Plugin plugin)
	{
		if (plugins == null)
			return;
		if (!plugins.contains(plugin))
			return;
		plugin.dispose();
		this.plugins.remove(plugin);
	}


	/**
	 * Set the absolute position of this component.
	 *
	 * @param x The absolute position x of the component.
	 * @param y The absolute position y of the component.
	 * @return this
	 */
	public GuiComponent setPos(int x, int y)
	{
		this.x = x;
		this.y = y;
		return this;
	}

	/**
	 * Set the relative position of this component.
	 *
	 * @param x The relative position x of the component.
	 * @param y The relative position y of the component.
	 * @return this
	 */
	public GuiComponent setPosRelative(int x, int y)
	{
		this.x = parent == null ? 0 : this.parent.x + x;
		this.y = parent == null ? 0 : this.parent.y + y;
		return this;
	}

	public void draw()
	{
		for (DrawNode drawNode : this.drawPipe)
			drawNode.draw(this.getX(), this.getY(), drawPipe, this.properties);
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

	protected void setSize(int width, int height)
	{
		this.width = width;
		this.height = height;
	}

	@Override
	protected void finalize() throws Throwable
	{
		for (Plugin plugin : plugins)
			plugin.dispose();
		super.finalize();
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

	public int getRelativeX()
	{
		return x - (parent == null ? 0 : parent.x);
	}

	public int getRelativeY()
	{
		return y - (parent == null ? 0 : parent.y);
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
