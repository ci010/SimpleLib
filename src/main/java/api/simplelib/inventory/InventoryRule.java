package api.simplelib.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.wrapper.InvWrapper;
import net.minecraftforge.items.wrapper.SidedInvWrapper;

/**
 * The rule of an {@link InventoryElement} should fallow.
 * <p>The {@link InventoryElement} could be a {@link InventorySlot} or multiple slots, {@link InventorySpace},
 * owned by {@link Inventory}.</p>
 *
 * @author ci010
 * @see Inventory
 */
public interface InventoryRule
{
	/**
	 * @param player the player.
	 * @return if a player should use this inventory.
	 */
	boolean isUsebleByPlayer(EntityPlayer player);

	/**
	 * @param stack the new item stack.
	 * @return if a item stack could place on this inventory.
	 */
	boolean isItemValid(ItemStack stack);

	/**
	 * @return The maximum size of item stack could be.
	 */
	int getInventoryStackLimit();

	InventoryRule COMMON = new InventoryRule()
	{
		@Override
		public boolean isUsebleByPlayer(EntityPlayer player)
		{
			return true;
		}

		@Override
		public boolean isItemValid(ItemStack stack)
		{
			return true;
		}

		@Override
		public int getInventoryStackLimit()
		{
			return 64;
		}
	};

	class SidedInvWrapIItemHandler extends SidedInvWrapper
	{
		private InventoryElement[] elements;

		public SidedInvWrapIItemHandler(Inventory inv, EnumFacing side)
		{
			super(inv.asSideInventory(), side);
			elements = inv.toArray();
		}

		@Override
		public ItemStack insertItem(int slot, ItemStack stack, boolean simulate)
		{

			if (stack == null)
				return null;

			int slot1 = getSlot(inv, slot, side);

			if (slot1 == -1)
				return stack;

			if (!inv.isItemValidForSlot(slot1, stack) || !inv.canInsertItem(slot1, stack, side))
				return stack;

			ItemStack stackInSlot = inv.getStackInSlot(slot1);

			int m;
			if (stackInSlot != null)
			{
				if (!ItemHandlerHelper.canItemStacksStack(stack, stackInSlot))
					return stack;

				m = Math.min(stack.getMaxStackSize(), elements[slot].getRule().getInventoryStackLimit())-stackInSlot.stackSize;

				if (stack.stackSize <= m)
				{
					if (!simulate)
					{
						ItemStack copy = stack.copy();
						copy.stackSize += stackInSlot.stackSize;
						inv.setInventorySlotContents(slot1, copy);
					}

					return null;
				}
				else
				{
					// copy the stack to not modify the original one
					stack = stack.copy();
					if (!simulate)
					{
						ItemStack copy = stack.splitStack(m);
						copy.stackSize += stackInSlot.stackSize;
						inv.setInventorySlotContents(slot1, copy);
						return stack;
					}
					else
					{
						stack.stackSize -= m;
						return stack;
					}
				}
			}
			else
			{
				m = Math.min(stack.getMaxStackSize(), elements[slot].getRule().getInventoryStackLimit());
				if (m < stack.stackSize)
				{
					// copy the stack to not modify the original one
					stack = stack.copy();
					if (!simulate)
					{
						inv.setInventorySlotContents(slot1, stack.splitStack(m));
						return stack;
					}
					else
					{
						stack.stackSize -= m;
						return stack;
					}
				}
				else
				{
					if (!simulate)
						inv.setInventorySlotContents(slot1, stack);
					return null;
				}
			}
		}
	}

	class InvWrapIItemHandler extends InvWrapper
	{
		private InventoryElement[] elements;

		public InvWrapIItemHandler(Inventory inv)
		{
			super(inv.asIInventory());
			elements = inv.toArray();
		}

		@Override
		public ItemStack insertItem(int slot, ItemStack stack, boolean simulate)
		{
			if (stack == null)
				return null;

			if (!getInv().isItemValidForSlot(slot, stack))
				return stack;

			ItemStack stackInSlot = getInv().getStackInSlot(slot);

			int m;
			if (stackInSlot != null)
			{
				if (!ItemHandlerHelper.canItemStacksStack(stack, stackInSlot))
					return stack;

				m = Math.min(stack.getMaxStackSize(), elements[slot].getRule().getInventoryStackLimit()) -
						stackInSlot.stackSize;

				if (stack.stackSize <= m)
				{
					if (!simulate)
					{
						ItemStack copy = stack.copy();
						copy.stackSize += stackInSlot.stackSize;
						getInv().setInventorySlotContents(slot, copy);
						getInv().markDirty();
					}
					return null;
				}
				else
				{
					if (!simulate)
					{
						ItemStack copy = stack.splitStack(m);
						copy.stackSize += stackInSlot.stackSize;
						getInv().setInventorySlotContents(slot, copy);
						getInv().markDirty();
						return stack;
					}
					else
					{
						stack.stackSize -= m;
						return stack;
					}
				}
			}
			else
			{
				m = Math.min(stack.getMaxStackSize(), elements[slot].getRule().getInventoryStackLimit());
				if (m < stack.stackSize)
				{
					if (!simulate)
					{
						getInv().setInventorySlotContents(slot, stack.splitStack(m));
						getInv().markDirty();
						return stack;
					}
					else
					{
						stack.stackSize -= m;
						return stack;
					}
				}
				else
				{
					if (!simulate)
					{
						getInv().setInventorySlotContents(slot, stack);
						getInv().markDirty();
					}
					return null;
				}
			}
		}
	}
}
