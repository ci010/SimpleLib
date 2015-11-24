package net.ci010.minecrafthelper.container;

import net.ci010.minecrafthelper.tileentity.TileEntityWrap;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;

/**
 * @author ci010
 */
public abstract class ContainerWrap extends Container
{
	private TileEntityWrap tileEntityWrap;

	public ContainerWrap(InventoryPlayer player, TileEntityWrap tileEntityWrap)
	{
		this.tileEntityWrap = tileEntityWrap;
		int index;
		for (index = 0; index < 3; ++index)
			for (int offset = 0; offset < 9; ++offset)
				this.addSlotToContainer(new Slot(player, offset + index * 9 + 9, 8 + offset * 18, 84 + index * 18));
		for (index = 0; index < 9; ++index)
			this.addSlotToContainer(new Slot(player, index, 8 + index * 18, 142));
	}
}
