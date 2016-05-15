package net.simplelib.interactive;

import test.interactive.Interactive;
import test.interactive.process.ProcessPipeline;
import api.simplelib.registry.ModTileEntity;
import api.simplelib.utils.ContextFactory;
import net.minecraft.inventory.IInventory;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.simplelib.interactive.inventory.InventoryCommon;

/**
 * @author ci010
 */
@ModTileEntity
public class TileEntityDummy extends TileEntity implements InventoryCommon.Listener
{
	public synchronized static TileEntity newTileEntity(Interactive.Entity entity)
	{
		Object update;
		if ((update = entity.getCapability(ProcessPipeline.DATA, null)) != null)
			return new Update(entity).load((ITickable) update);
		return new TileEntityDummy(entity);
	}

	public TileEntityDummy(Interactive.Entity entity)
	{
		super();
		real = entity;
	}

	@ModTileEntity
	public static class Update extends TileEntityDummy implements ITickable
	{
		private ITickable updater;

		public Update(Interactive.Entity entity)
		{
			super(entity);
		}

		public TileEntityDummy load(ITickable box)
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

	protected Interactive.Entity real;
	protected boolean sensitive;

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing)
	{
		return this.real.hasCapability(capability, facing) || super.hasCapability(capability, facing);
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing)
	{
		T c = this.real.getCapability(capability, facing);
		if (c != null)
			return c;
		return super.getCapability(capability, facing);
	}

	@Override
	public void readFromNBT(NBTTagCompound tag)
	{
		super.readFromNBT(tag);
		real = InteractiveMetadata.getInstance(tag.getString("interactive_id"))
				.createEntity(ContextFactory.newContext(worldObj));
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
