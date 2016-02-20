package net.simplelib.interactive.inventory;

import com.google.common.collect.ImmutableMap;
import net.minecraft.nbt.NBTTagCompound;
import net.simplelib.common.nbt.ITagSerial;
import api.simplelib.utils.GenericUtil;
import net.simplelib.interactive.IProperty;
import api.simplelib.interactive.Interactive;
import net.simplelib.interactive.InteractiveEntity;

/**
 * @author ci010
 */
public class PropertyDataInventory implements IProperty
{
	protected InventoryManagerImpl slots;

	@Override
	public void init(Interactive interactive)
	{
		slots = new InventoryManagerImpl();
		((api.simplelib.interactive.inventory.Inventory) interactive).provideInventory(slots);
		if (slots.isEmpty())
			slots = null;
	}

	private class Inv implements ITagSerial
	{
		private ImmutableMap<String, Inventory> inv;

		ITagSerial apply(InventoryManagerImpl manager)
		{
			inv = manager.apply();
			return this;
		}

		@Override
		public void readFromNBT(NBTTagCompound tag)
		{
			for (Inventory inventory : inv.values())
				inventory.readFromNBT(tag);
		}

		@Override
		public void writeToNBT(NBTTagCompound tag)
		{
			for (Inventory inventory : inv.values())
				inventory.writeToNBT(tag);
		}
	}

	@Override
	public ITagSerial buildProperty()
	{
		return new Inv().apply(this.slots);
	}

	@Override
	public boolean shouldApply(Interactive interactive)
	{
		return interactive instanceof api.simplelib.interactive.inventory.Inventory;
	}

	public ImmutableMap<String, Inventory> getInventories(InteractiveEntity entity)
	{
		return GenericUtil.cast(entity.get(Inv.class));
	}
}
