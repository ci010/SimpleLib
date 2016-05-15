package net.simplelib.inventory;

import api.simplelib.gui.ComponentAPI;
import api.simplelib.gui.components.GuiComponent;
import net.minecraft.util.ResourceLocation;

/**
 * @author ci010
 */
public class ComponentSlot extends GuiComponent
{
	@Override
	public ResourceLocation getDraw()
	{
		return ComponentAPI.LOC_DRAW_TEXTURE;
	}

	@Override
	public int getWidth()
	{
		return 18;
	}

	@Override
	public int getHeight()
	{
		return 18;
	}
}
