package net.ci010.minecrafthelper.test;

import net.ci010.minecrafthelper.container.ContainerWrap;
import net.ci010.minecrafthelper.tileentity.TileEntityWrap;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

/**
 * @author ci010
 */
public class MachineRegistry implements IGuiHandler
{
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		return new ContainerWrap(player.inventory, (TileEntityWrap) world.getTileEntity(new BlockPos(x, y, z)))
		{
			@Override
			public boolean canInteractWith(EntityPlayer playerIn)
			{
				return false;
			}
		};
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		return new GuiContainer(new ContainerWrap(player.inventory, (TileEntityWrap) world.getTileEntity(new BlockPos(x, y, z)))
		{
			@Override
			public boolean canInteractWith(EntityPlayer playerIn)
			{
				return false;
			}
		})
		{
			@Override
			protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
			{
//					for (GuiComponent guiComponent : gui)
//						guiComponent.draw();
			}
		};
	}
}
