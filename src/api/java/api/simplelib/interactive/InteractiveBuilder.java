package api.simplelib.interactive;

import net.simplelib.gui.GuiProvider;
import net.simplelib.network.GuiHandler;

/**
 * @author ci010
 */
public class InteractiveBuilder
{
	private int guiId;
	private GuiProvider provider;

	public static InteractiveBuilder newBuilder()
	{
		return new InteractiveBuilder();
	}

	public InteractiveBuilder applyGui(GuiHandler.ContainerProvider provider)
	{
		GuiHandler.addContainerProvider(provider);
		return this;
	}
}
