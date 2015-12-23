package net.ci010.minecrafthelper.deprecated;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;

/**
 * @author ci010
 */
public interface IGuiPortHandler
{
	ISyncPort getPort(EntityPlayer player, int x, int y, int z);

	GuiScreen getGui(EntityPlayer player, int x, int y, int z);
}
