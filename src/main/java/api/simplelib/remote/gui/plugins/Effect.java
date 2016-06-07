package api.simplelib.remote.gui.plugins;

import api.simplelib.remote.gui.components.GuiComponent;

/**
 * @author ci010
 */
public class Effect implements Plugin
{
	private Plugin plugin;
	private GuiComponent component;
	private int current;

	public Effect(Plugin plugin, int second)
	{
		this.plugin = plugin;
		this.current = second;
	}

	public void onTick()
	{

	}

	@Override
	public void plugin(GuiComponent component)
	{
		this.component = component;
		plugin.plugin(component);
	}

	@Override
	public void dispose()
	{
		plugin.dispose();
	}
}
