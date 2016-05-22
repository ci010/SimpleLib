package api.simplelib.gui.seril;

import api.simplelib.gui.components.GuiComponent;
import com.google.gson.*;

import java.lang.reflect.Type;

/**
 * @author ci010
 */
public class GuiComponentSeril implements JsonSerializer<GuiComponent>, JsonDeserializer<GuiComponent>
{
	@Override
	public GuiComponent deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
	{
		JsonObject body = json.getAsJsonObject();
		int x = body.get("x").getAsInt(), y = body.get("y").getAsInt(),
				width = body.get("width").getAsInt(), height = body.get("height").getAsInt();
		String parent = body.get("parent").getAsString();
		JsonArray children = body.get("children").getAsJsonArray();
		for (JsonElement child : children)
		{

		}
		JsonArray nodes = body.get("draw-node").getAsJsonArray();
		for (JsonElement node : nodes)
		{

		}
		JsonObject property = body.get("property").getAsJsonObject();
		return null;
	}

	@Override
	public JsonElement serialize(GuiComponent src, Type typeOfSrc, JsonSerializationContext context)
	{
		return null;
	}
}
