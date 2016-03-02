package api.simplelib;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.world.World;

/**
 * @author ci010
 */
public interface IContainerProvider
{
	Container getContainer(EntityPlayer player, World world, int x, int y, int z);

	GuiContainer getGuiContainer(EntityPlayer player, World world, int x, int y, int z);
}
