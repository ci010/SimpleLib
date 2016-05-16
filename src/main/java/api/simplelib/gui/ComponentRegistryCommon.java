package api.simplelib.gui;

import api.simplelib.gui.animation.Controller;
import api.simplelib.gui.components.GuiComponent;
import api.simplelib.registry.ModProxy;
import api.simplelib.seril.IJsonSerializer;
import api.simplelib.utils.GenericUtil;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.Gson;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;

import java.util.Map;
import java.util.Set;

/**
 * @author ci010
 */
@ModProxy(side = Side.SERVER, genericType = ComponentRepository.class)
public class ComponentRegistryCommon implements ComponentRepository
{
	private static Set<Class<?>> supportedType = Sets.newHashSet(new Class<?>[]{int.class, float.class,
																				short.class, long.class,
																				double.class, boolean.class,
																				char.class, Float.class, Long.class,
																				Character.class, Double.class,
																				Short.class, String.class,
																				Integer.class, Boolean.class,
																				Enum.class});
	private BiMap<ResourceLocation, DrawNode> map = HashBiMap.create();
	private BiMap<ResourceLocation, Controller> controllerMap = HashBiMap.create();
	private Map<ResourceLocation, Properties.Key> propMap = Maps.newHashMap();
	private Map<Class, IJsonSerializer> serializableMap = Maps.newHashMap();

	protected void register()
	{
		registerDrawNode(ComponentAPI.LOC_DRAW_TEXTURE, null);
		registerDrawNode(ComponentAPI.LOC_DRAW_BACKGROUND, null);
		registerDrawNode(ComponentAPI.LOC_DRAW_BORDER_TEXTS, null);
		registerDrawNode(ComponentAPI.LOC_DRAW_STRING, null);

		registerController(ComponentAPI.LOC_CTRL_FADE_OUT, null);
		registerController(ComponentAPI.LOC_CTRL_FADE_OUT, null);
		registerController(ComponentAPI.LOC_CTRL_DEFAULT, null);
	}

	public DrawNode fetchDrawer(ResourceLocation location)
	{
		return map.get(location);
	}

	public Controller fetchController(ResourceLocation location)
	{
		return controllerMap.get(location);
	}

	@Override
	public <T> Properties.Key<T> fetchKey(DrawNode node, Class<T> type)
	{
		Gson gson;
		return fetchKey(map.inverse().get(node), type);
	}

	@Override
	public <T> Properties.Key<T> fetchKey(Controller controller, Class<T> type)
	{
		return fetchKey(controllerMap.inverse().get(controller), type);
	}

	@Override
	public <T> Properties.Key<T> fetchKey(ResourceLocation location, Class<T> type)
	{
		return GenericUtil.cast(propMap.get(new ResourceLocation(location.toString().concat(".")
				.concat(type.getSimpleName().toLowerCase()))));
	}

	public void registerDrawNode(ResourceLocation location, DrawNode drawable)
	{
		if (map.containsKey(location))
			throw new IllegalArgumentException("duplicate key");
		if (this.getClass() == ComponentRegistryCommon.class)
			map.put(location, new ClientElement(location));
		else
			map.put(location, drawable);
	}

	public <T> void registerSerializer(IJsonSerializer<T> serializer, Class<T> type)
	{
		serializableMap.put(type, serializer);
	}

	public void registerController(ResourceLocation location, Controller controller)
	{
		if (controllerMap.containsKey(location))
			throw new IllegalArgumentException("duplicate key");
		if (this.getClass() == ComponentRegistryCommon.class)
			controllerMap.put(location, new ClientElement(location));
		else
			controllerMap.put(location, controller);
	}

	class ClientElement implements DrawNode, Controller
	{
		ResourceLocation loc;

		public ClientElement(ResourceLocation name)
		{
			this.loc = name;
		}

		@Override
		public void draw(int x, int y, Properties properties) {}

		@Override
		public void onLoad(GuiComponent component)
		{}

		@Override
		public void draw(GuiComponent component) {}

		@Override
		public void onRemoved(GuiComponent component)
		{}
	}
}
