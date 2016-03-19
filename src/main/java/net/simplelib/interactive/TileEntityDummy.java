package net.simplelib.interactive;

import api.simplelib.common.ModHandler;
import api.simplelib.interactive.process.ProcessPipeline;
import api.simplelib.tileentity.ModTileEntity;
import com.google.common.base.Preconditions;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.simplelib.interactive.inventory.InventoryCommon;

/**
 * @author ci010
 */
@ModTileEntity
public class TileEntityDummy extends TileEntity implements InventoryCommon.Listener
{
	private static InteractiveEntity cache;

	public synchronized static TileEntity newTileEntity(InteractiveEntity entity)
	{
		cache = entity;
		Object update;
		if ((update = entity.get(ProcessPipeline.class)) != null)
			return new Update(entity).load((ITickable) update); ;
		return new TileEntityDummy(entity);
	}

	public TileEntityDummy(InteractiveEntity entity)
	{
		super();
		real = entity;
	}

	@ModTileEntity
	public static class Update extends TileEntityDummy implements ITickable
	{
		private ITickable updater;

		public Update(InteractiveEntity entity)
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

	public InteractiveEntity getEntity()
	{
		return this.real;
	}

	protected InteractiveEntity real;
	protected boolean sensitive;

//	@Override
//	public void readFromNBT(NBTTagCompound tag)
//	{
//		super.readFromNBT(tag);
//		real = InteractiveMetadata.getInstance(tag.getString("interactive_id"))
//				.createEntity(this.worldObj);
//		real.readFromNBT(tag);
//	}
//
//	@Override
//	public void writeToNBT(NBTTagCompound tag)
//	{
//		super.writeToNBT(tag);
//		real.writeToNBT(tag);
//	}


	@Override
	public void onInventoryChange(IInventory inventory)
	{
		if (sensitive)
			this.markDirty();
	}


	@ModHandler
	public static class Handler
	{
		@SubscribeEvent
		public void onTileAdd(AttachCapabilitiesEvent.TileEntity event)
		{
			if (event.getTileEntity() instanceof TileEntityDummy)
				event.addCapability(new ResourceLocation(Preconditions.checkNotNull(cache).getId()), cache);
		}
	}
}
