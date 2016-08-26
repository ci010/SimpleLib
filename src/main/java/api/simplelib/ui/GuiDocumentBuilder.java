package api.simplelib.ui;

import api.simplelib.capabilities.CapabilityBuilder;
import api.simplelib.ui.elements.Element;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import net.minecraft.util.ResourceLocation;

import java.lang.reflect.Type;

/**
 * @author ci010
 */
@CapabilityBuilder(GuiDocument.class)
public interface GuiDocumentBuilder
{
	GuiDocumentBuilder addComponent(Element component);

	GuiDocumentBuilder addComponent(ResourceLocation location);


}
