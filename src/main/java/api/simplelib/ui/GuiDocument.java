package api.simplelib.ui;

import api.simplelib.Overview;
import api.simplelib.ui.elements.Element;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

import java.util.Collection;

/**
 * @author ci010
 */
public interface GuiDocument extends Overview<Element>
{
	@CapabilityInject(GuiDocument.class)
	Capability<GuiDocument> CAPABILITY = null;

	Collection<Element> getRoots();

	ResourceLocation location();
}
