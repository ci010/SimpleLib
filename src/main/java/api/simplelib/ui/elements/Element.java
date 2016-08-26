package api.simplelib.ui.elements;

import api.simplelib.Pipeline;
import api.simplelib.seril.IJsonSerializer;
import api.simplelib.ui.ElementRepository;
import api.simplelib.ui.ElementState;
import api.simplelib.ui.Properties;
import api.simplelib.ui.node.DrawNode;
import api.simplelib.ui.plugins.Plugin;
import api.simplelib.vars.VarRef;
import com.google.common.collect.Lists;
import com.google.gson.*;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nonnull;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author ci010
 */
public class Element
{
	private String name = StringUtils.EMPTY;

	public ElementState state = ElementState.NORMAL;
	protected Transform transform = new Transform();

	private Properties properties;
	private List<Plugin> plugins;
	private Pipeline<DrawNode> nodes;

	private Element parent;
	protected List<Element> children;
	private List<Element> view;

	public String getName()
	{
		return name;
	}

	public Element setName(String name)
	{
		if (name != null)
			this.name = name;
		else this.name = StringUtils.EMPTY;
		return this;
	}

	public Properties getProperties()
	{
		if (properties == null)
			properties = ElementRepository.repository.newProperty();
		return properties;
	}

	public Element getParent()
	{
		return parent;
	}

	@Nonnull
	public List<Element> getChildren()
	{
		if (view == null)
			return Collections.emptyList();
		return view;
	}

	protected List<Plugin> getPlugins()
	{
		if (plugins == null)
			plugins = Lists.newArrayList();
		return plugins;
	}

	protected Pipeline<DrawNode> getDrawPipe()
	{
		if (nodes == null)
			nodes = ElementRepository.repository.newDrawPipe();
		return nodes;
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
	public Element add(Element component)
	{
		if (component.parent != null)
			throw new IllegalArgumentException("The component " + component.getName() + " has already had a parent!");
		if (children == null)
		{
			children = Lists.newArrayList();
			view = Collections.unmodifiableList(children);
		}
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
		plugin.dispose(this);
		this.plugins.remove(plugin);
	}

	public void draw()
	{
		for(DrawNode drawNode : this.nodes)
			drawNode.apply(this);
		if (children != null && !children.isEmpty())
			for(Element child : children)
				child.draw();
	}

	@Override
	protected void finalize() throws Throwable
	{
		for(Plugin plugin : plugins)
			plugin.dispose(this);
		super.finalize();
	}

	/**
	 * Set the relative position of this component.
	 *
	 * @param x The relative position x of the component.
	 * @param y The relative position y of the component.
	 * @return this
	 */
	public Element setPosRelative(int x, int y)
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

	public static class Transform
	{
		public int x, y, width, height;

		public Transform() {}

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

		public void translate(int x, int y)
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

		public boolean container(int x, int y)
		{
			return x > this.x && x < this.x + this.width &&
					y > this.y && y < this.y + this.height;
		}
	}


	public static Gson serializer()
	{
		return new GsonBuilder()
				.registerTypeAdapter(VarRef.class, new IJsonSerializer<VarRef>()
				{
					@Override
					public JsonElement serialize(VarRef src, Type typeOfSrc, JsonSerializationContext context)
					{
						return new JsonPrimitive("ref:".concat(src.getName()));
					}

					@Override
					public VarRef deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
					{
						return null;
					}
				})
				.registerTypeAdapter(Element.class, new IJsonSerializer<Element>()
				{
					@Override
					public JsonElement serialize(Element src, Type typeOfSrc, JsonSerializationContext context)
					{
						JsonElement serialize = context.serialize(src, src.getClass());
						JsonObject obj = serialize.getAsJsonObject();
						JsonArray trans = new JsonArray();
						trans.add(new JsonPrimitive(src.transform().x));
						trans.add(new JsonPrimitive(src.transform().y));
						trans.add(new JsonPrimitive(src.transform().width));
						trans.add(new JsonPrimitive(src.transform().height));
						obj.add("transform", trans);

						if (src.plugins != null)
						{
							JsonArray arr = new JsonArray();
							for(Plugin plugin : src.plugins)
								arr.add(new JsonPrimitive(plugin.getClass().getSimpleName()));
							obj.add("plugins", arr);
						}
						if (src.nodes != null)
						{
							for(DrawNode node : src.nodes)
							{


							}
						}

//				if (src.drawPipe != null)
//				{
//					JsonArray arr = new JsonArray();
//					for (DrawNode drawNode : src.drawPipe)
//						arr.add(new JsonPrimitive(ElementRepository.repository.getLocation(drawNode).toString()));
//					obj.add("draw_pipe", arr);
//				}


						return serialize;
					}

					@Override
					public Element deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
					{
						Element element = new Element();
						JsonObject obj = json.getAsJsonObject();
						for(Map.Entry<String, JsonElement> entry : obj.entrySet())
						{
							String key = entry.getKey();
							if (key.equals("transform"))
							{

							}
							else if (key.equals("plugins"))
							{

							}
							else if (key.equals("drawPipe"))
							{

							}
							else
							{
								if (entry.getValue().isJsonPrimitive())
								{
									JsonPrimitive primitive = entry.getValue().getAsJsonPrimitive();
									if (primitive.isString())
									{
										String str = primitive.getAsString();
										String[] split = str.split(":");
										if (split.length == 1)
											element.getProperties().str(key).set(str);
										else
										{
//											VarRefMapping varRefWrap = new VarRefMapping(split[1]);
										}
									}
									else if (primitive.isNumber())
										element.getProperties().num(key).set(primitive.getAsNumber());
									else if (primitive.isBoolean())
										element.getProperties().bool(key).set(primitive.getAsBoolean());
								}
								else
								{

								}

							}
						}
						return null;
					}
				}).create();
	}
}
