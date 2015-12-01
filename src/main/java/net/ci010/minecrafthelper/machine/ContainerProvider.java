package net.ci010.minecrafthelper.machine;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.world.World;

/**
 * @author ci010
 */
public interface ContainerProvider
{
	Container getContainer(int ID, EntityPlayer player, World world, int x, int y, int z);

	GuiContainer getGuiContainer(int ID, EntityPlayer player, World world, int x, int y, int z);

	boolean equals(Object o);
}
