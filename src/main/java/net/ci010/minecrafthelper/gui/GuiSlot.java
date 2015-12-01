package net.ci010.minecrafthelper.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.inventory.Slot;

/**
 * @author ci010
 */
public class GuiSlot extends GuiComponent
{
	private Slot slot;
	private TileTexture slotSample;

	public GuiSlot(Slot slot)
	{
		this.slot = slot;
	}

	@Override
	public void draw()
	{
		slotSample.draw();
	}
}
