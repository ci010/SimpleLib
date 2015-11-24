package net.ci010.minecrafthelper.test;

import net.ci010.minecrafthelper.container.ContainerWrap;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;

/**
 * @author ci010
 */
public abstract class GuiContainerWrap extends GuiContainer
{
	public GuiContainerWrap(Container inventorySlotsIn)
	{
		super(inventorySlotsIn);
	}
}
