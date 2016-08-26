package api.simplelib.utils;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import net.minecraft.util.JsonUtils;

/**
 * @author ci010
 */
public class GsonUtils
{
	public static String getString(JsonObject object, String name, String fallback)
	{
		return JsonUtils.getString(object, name, fallback);
	}

	public static Number getNumber(JsonObject object, String name, Number fallback)
	{
		return object.has(name) ? object.get(name).getAsNumber() : fallback;
	}

	public static boolean getBoolean(JsonObject object, String name, boolean fallback)
	{
		return JsonUtils.getBoolean(object, name, fallback);
	}

	public static PrimitiveType inspect(JsonPrimitive primitive)
	{
		if (primitive.isNumber())
			return PrimitiveType.inspect(primitive.getAsNumber());
		else return primitive.isBoolean() ? PrimitiveType.BOOL : null;
	}
}
