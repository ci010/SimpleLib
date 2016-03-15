package api.simplelib.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.ResourceLocation;

/**
 * @author ci010
 */
public class GuiHub extends Gui
{
	protected Minecraft client = Minecraft.getMinecraft();

	public void start(ResourceLocation location)
	{
		client.renderEngine.bindTexture(location);
	}

	public void end()
	{
		client.renderEngine.bindTexture(icons);
	}
}
