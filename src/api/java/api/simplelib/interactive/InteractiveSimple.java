package api.simplelib.interactive;

import api.simplelib.container.ContainerCommon;
import api.simplelib.container.IContainerProvider;
import api.simplelib.interactive.action.ActionOpenGui;
import api.simplelib.container.ContainerProvider;
import api.simplelib.gui.GuiProvider;

/**
 * A simple solution for the {@link Interactive} with Gui.
 * <p>I highly recommend you to use {@link ContainerCommon} as the Container here.</p>
 *
 * @author ci010
 */
public abstract class InteractiveSimple implements Interactive
{
	private IContainerProvider provider;

	public InteractiveSimple()
	{
		this.provider = new ContainerProvider(this, getGui());
	}

	public abstract GuiProvider getGui();

	@Override
	public Action getAction()
	{
		return new ActionOpenGui(provider);
	}
}
