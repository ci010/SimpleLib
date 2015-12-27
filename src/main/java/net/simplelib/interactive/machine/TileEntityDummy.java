package net.simplelib.interactive.machine;

import net.minecraft.inventory.IInventory;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.gui.IUpdatePlayerListBox;
import net.minecraft.tileentity.TileEntity;
import net.simplelib.annotation.type.ModTileEntity;
import net.simplelib.interactive.InteractiveEntityUpdate;
import net.simplelib.interactive.InteractiveMetadata;
import net.simplelib.interactive.Inventory;

/**
 * @author ci010
 */
@ModTileEntity
public class TileEntityDummy extends TileEntity implements IUpdatePlayerListBox, Inventory.Listener
{
	protected InteractiveEntityUpdate real;
	protected boolean sensitve;

	public TileEntityDummy load(InteractiveEntityUpdate real)
	{
		this.real = real;
		return this;
		//TODO think about if there is a better way to handle the dirty problem
	}

	public TileEntityDummy saveSensitive()
	{
		sensitve = true;
		return this;
	}

	@Override
	public void readFromNBT(NBTTagCompound tag)
	{
		super.readFromNBT(tag);
		real = (InteractiveEntityUpdate) InteractiveMetadata.getMetaData(tag.getString("interactive_id"))
				.createEntity(this.worldObj);
		real.readFromNBT(tag);
	}

	@Override
	public void writeToNBT(NBTTagCompound tag)
	{
		super.writeToNBT(tag);
		real.writeToNBT(tag);
	}

	@Override
	public void update()
	{
		real.update();
	}

	@Override
	public void onInventoryChange(IInventory inventory)
	{
		if (sensitve)
			this.markDirty();
	}
}
