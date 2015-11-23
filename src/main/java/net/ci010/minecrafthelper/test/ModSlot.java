package net.ci010.minecrafthelper.test;

import net.minecraft.inventory.Slot;

/**
 * @author ci010
 */
public class ModSlot implements GuiComponent
{
	private Slot slot;
	private TextureInfo slotSample;

	public ModSlot(Slot slot)
	{
		this.slot = slot;
	}


	@Override
	public void draw()
	{

	}
}
