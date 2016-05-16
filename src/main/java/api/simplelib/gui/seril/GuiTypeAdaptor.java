package api.simplelib.gui.seril;

import api.simplelib.gui.components.GuiComponent;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

/**
 * @author ci010
 */
public class GuiTypeAdaptor extends TypeAdapter<GuiComponent>
{
	@Override
	public void write(JsonWriter out, GuiComponent value) throws IOException
	{
		out.beginObject();
		{
			out.name("type").value(value.getClass().getSimpleName());
			out.name("width").value(value.getWidth());
			out.name("height").value(value.getHeight());
		}
		out.endObject();
	}

	@Override
	public GuiComponent read(JsonReader in) throws IOException
	{
		return null;
	}
}
