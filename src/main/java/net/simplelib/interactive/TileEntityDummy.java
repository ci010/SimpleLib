package net.simplelib.interactive;

import api.simplelib.interactive.process.ProcessPipeline;
import api.simplelib.tileentity.ModTileEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.gui.IUpdatePlayerListBox;
import net.minecraft.tileentity.TileEntity;
import net.simplelib.interactive.inventory.InventoryCommon;

/**
 * @author ci010
 */
@ModTileEntity
public class TileEntityDummy extends TileEntity implements InventoryCommon.Listener
{
	public static TileEntity newTileEntity(InteractiveEntity entity)
	{
		Object update;
		if ((update = entity.get(ProcessPipeline.class)) != null)
			return new Update().load((IUpdatePlayerListBox) update).load(entity);
		return new TileEntityDummy().load(entity);
	}

	@ModTileEntity
	public static class Update extends TileEntityDummy implements IUpdatePlayerListBox
	{
		private IUpdatePlayerListBox updater;

		public TileEntityDummy load(IUpdatePlayerListBox box)
		{
			this.updater = box;
			return this;
		}

		@Override
		public void update()
		{
			if (!this.worldObj.isRemote)
				updater.update();
		}
	}

	public InteractiveEntity getEntity()
	{
		return this.real;
	}

	protected InteractiveEntity real;
	protected boolean sensitive;

	public TileEntityDummy load(InteractiveEntity real)
	{
		this.real = real;
		return this;
	}

	@Override
	public void readFromNBT(NBTTagCompound tag)
	{
		super.readFromNBT(tag);
		real = InteractiveMetadata.getInstance(tag.getString("interactive_id"))
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
	public void onInventoryChange(IInventory inventory)
	{
		if (sensitive)
			this.markDirty();
	}
}
