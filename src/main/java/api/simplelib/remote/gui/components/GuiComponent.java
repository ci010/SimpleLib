package api.simplelib.remote.gui.components;

import api.simplelib.Pipeline;
import api.simplelib.remote.gui.ComponentRepository;
import api.simplelib.remote.gui.Properties;
import api.simplelib.remote.gui.node.DrawNode;
import api.simplelib.remote.gui.plugins.Plugin;
import com.google.common.collect.Lists;
import net.minecraft.inventory.Container;

import java.util.List;

/**
 * @author ci010
 */
public class GuiComponent
{
	protected Transform transform = new Transform();
	private Pipeline<DrawNode> drawPipe;
	private Properties properties;
	private List<Plugin> plugins;

	private GuiComponent parent;
	protected List<GuiComponent> children;

	public Properties getProperties()
	{
		if (properties == null)
			properties = ComponentRepository.repository.newProperty();
		return properties;
	}

	public GuiComponent getParent()
	{
		return parent;
	}

	protected List<Plugin> getPlugins()
	{
		if (plugins == null)
			plugins = Lists.newArrayList();
		return plugins;
	}

	protected Pipeline<DrawNode> getDrawPipe()
	{
		if (drawPipe == null)
			drawPipe = ComponentRepository.repository.newDrawPipe();
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

	public void draw()
	{
		for (DrawNode drawNode : this.drawPipe)
			drawNode.draw(this.transform, drawPipe, this.properties);
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

	@Override
	protected void finalize() throws Throwable
	{
		for (Plugin plugin : plugins)
			plugin.dispose();
		super.finalize();
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
		transform.x = parent == null ? 0 : this.parent.transform.x + x;
		transform.y = parent == null ? 0 : this.parent.transform.y + y;
		return this;
	}

	public int getRelativeX()
	{
		return transform.x - (parent == null ? 0 : parent.transform.x);
	}

	public int getRelativeY()
	{
		return transform.y - (parent == null ? 0 : parent.transform.y);
	}

	public Transform transform()
	{
		if (transform == null)
			if (parent != null)
				transform = new Transform(parent.transform());
			else
				transform = new Transform();
		return transform;
	}

	public class Transform
	{
		public int x, y, width, height;

		public Transform()
		{}

		public Transform(int x, int y, int width, int height)
		{
			this.x = x;
			this.y = y;
			this.width = width;
			this.height = height;
		}

		public Transform(Transform transform)
		{
			this.setPos(transform);
			this.setSize(transform);
		}

		public void offset(int x, int y)
		{
			this.x += x;
			this.y += y;
		}

		/**
		 * Set the absolute position of this component.
		 *
		 * @param x The absolute position x of the component.
		 * @param y The absolute position y of the component.
		 * @return this
		 */
		public void setPos(int x, int y)
		{
			this.x = x;
			this.y = y;
		}

		public void setPos(Transform transform)
		{
			this.x = transform.x;
			this.y = transform.y;
		}

		public void setSize(int width, int height)
		{
			this.height = height;
			this.width = width;
		}

		public void setSize(Transform transform)
		{
			this.width = transform.width;
			this.height = transform.height;
		}
	}

}
