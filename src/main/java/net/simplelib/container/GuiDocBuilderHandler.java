package net.simplelib.container;

import api.simplelib.capabilities.CapabilityBuilderHandler;
import api.simplelib.ui.GuiDocument;
import api.simplelib.ui.GuiDocumentBuilder;
import api.simplelib.ui.elements.Element;
import com.google.common.collect.Sets;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import javax.annotation.Nonnull;
import java.util.Set;

/**
 * @author ci010
 */
public class GuiDocBuilderHandler implements CapabilityBuilderHandler<GuiDocumentBuilder>
{
	@Override
	public GuiDocumentBuilder createBuilder(Object contextSrc)
	{
		return new DocBuilderImpl();
	}

	private class DocBuilderImpl implements GuiDocumentBuilder
	{
		Set<Element> elementSet = Sets.newHashSet();

		@Override
		public GuiDocumentBuilder addComponent(Element component)
		{
			elementSet.add(component);
			return null;
		}

		@Override
		public GuiDocumentBuilder addComponent(ResourceLocation location)
		{
			//TODO deserialize
			return null;
		}
	}

	@Override
	public ICapabilityProvider build(GuiDocumentBuilder docBuilder, Object context)
	{
		return null;
	}

	@Override
	public Capability<?>[] allCapability()
	{
		return new Capability<?>[]{GuiDocument.CAPABILITY};
	}

	@Nonnull
	@Override
	public ResourceLocation getName()
	{
		return new ResourceLocation("document");
	}
}
