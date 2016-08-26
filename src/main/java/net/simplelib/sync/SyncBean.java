package net.simplelib.sync;

import api.simplelib.sync.AttributeFactory;
import api.simplelib.sync.UpdateMode;
import api.simplelib.utils.Consumer;
import api.simplelib.utils.GsonUtils;
import api.simplelib.utils.PrimitiveType;
import com.google.common.base.Predicate;
import com.google.gson.*;
import net.minecraft.util.JsonUtils;

import javax.annotation.Nullable;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.Set;

/**
 * @author ci010
 */
public class SyncBean implements Consumer<AttributeFactory>
{
	String[] name;
	UpdateMode[] mode;
	PrimitiveType[] type;
	Object[] defaultValues;
	Number[] min, max;
	int size;

	public SyncBean(int size)
	{
		this.size = size;
		name = new String[size];
		mode = new UpdateMode[size];
		type = new PrimitiveType[size];
		defaultValues = new Object[size];
		min = new Number[size];
		max = new Number[size];
	}

	static JsonDeserializer<Consumer<AttributeFactory>> deserializer = new JsonDeserializer<Consumer<AttributeFactory>>()
	{
		@Override
		public Consumer<AttributeFactory> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
		{
			JsonObject object = json.getAsJsonObject();
			Set<Map.Entry<String, JsonElement>> set = object.entrySet();
			SyncBean bean = new SyncBean(set.size());
			int i = 0;
			for (Map.Entry<String, JsonElement> entry : set)
			{
				JsonObject o = entry.getValue().getAsJsonObject();
				bean.name[i] = JsonUtils.getString(o, "name", entry.getKey());

				String tpString = JsonUtils.getString(o, "type", "INT").toUpperCase();
				if (!tpString.equals("STRING"))
					bean.type[i] = PrimitiveType.of(tpString.toUpperCase());

				bean.mode[i] = UpdateMode.valueOf(JsonUtils.getString(o, "mode", "LAZY"));

				JsonElement dfEle = o.get("default");
				switch (bean.type[i])
				{
					case BOOL:
						bean.defaultValues[i] = dfEle != null && dfEle.getAsBoolean();
						break;
					case BYTE:
					case SHORT:
					case INT:
					case LONG:
					case FLOAT:
					case DOUBLE:
						bean.defaultValues[i] = GsonUtils.getNumber(o, "default", 0);
						break;
					default:
						if (tpString.toLowerCase().equals("string"))
							bean.defaultValues[i] = GsonUtils.getString(o, "default", "");
						else if (dfEle == null)
						{
							bean.type[i] = PrimitiveType.INT;
							bean.defaultValues[i] = 0;
						}
						else
						{
							PrimitiveType type = GsonUtils.inspect(dfEle.getAsJsonPrimitive());
							if (type == null)
								bean.defaultValues[i] = GsonUtils.getString(o, "default", "");
							else
							{
								bean.type[i] = type;
								bean.defaultValues[i] = type.defaultValue();
							}
						}
						break;
				}
				++i;
			}
			return bean;
		}
	};

	@Override
	public void accept(@Nullable AttributeFactory input)
	{
		for (int i = 0; i < size; i++)
		{
			PrimitiveType primitiveType = type[i];
			if (primitiveType == null)
				input.newString(name[i], (String) defaultValues[i], mode[i]);
			else switch (primitiveType)
			{
				case BOOL:
					input.newBoolean(name[i], (Boolean) defaultValues[i], mode[i]);
					break;
				case BYTE:
				case SHORT:
				case INT:
				case LONG:
				case FLOAT:
				case DOUBLE:
					if (min[i] != null)
						input.newNumber(name[i], (Number) defaultValues[i], min[i], max[i], mode[i]);
					else
						input.newNumber(name[i], (Number) defaultValues[i], mode[i]);
					break;
			}
		}
	}
}
