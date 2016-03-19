package test.api.item.usage;

import api.simplelib.common.Nullable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import test.api.HitInfo;
import test.api.stack.ComponentStack;

/**
 * @author ci010
 */
public interface RightClickAction
{
	/**
	 * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
	 */
	ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn);

	/**
	 * Called when the player finishes using this Item (E.g. finishes eating.). Not called when the player stops using
	 * the Item before the action is complete.
	 */
	ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityPlayer playerIn);

	@Nullable
	BlockHandler getBlockHandler();

	@Nullable
	EntityHandler getEntityHandler();

	interface EntityHandler
	{
		/**
		 * Returns true if the item can be used on the given entity, e.g. shears on sheep.
		 */
		boolean onInteract(ComponentStack stack, EntityPlayer playerIn, EntityLivingBase target);
	}

	interface BlockHandler
	{

		/**
		 * This is called when the item is used, before the block is activated.
		 *
		 * @param stack  The Item Stack
		 * @param player The Player that used the item
		 * @return Return true to prevent any further processing.
		 */
		boolean onItemUseFirst(ItemStack stack, EntityPlayer player, HitInfo info);

		/**
		 * after {@link #onItemUseFirst(ItemStack, EntityPlayer, HitInfo)}
		 */
		boolean onItemUse(ItemStack stack, EntityPlayer playerIn, HitInfo info);

		/**
		 * Called each tick while using an item.
		 *
		 * @param stack  The Item being used
		 * @param player The Player using the item
		 * @param count  The amount of time in tick the item has been used for continuously
		 */
		void onUsingTick(ItemStack stack, EntityPlayer player, int count);

		/**
		 * Called when the player stops using an Item (stops holding the right mouse button).
		 */
		void onPlayerStoppedUsing(ItemStack stack, World worldIn, EntityPlayer playerIn, int timeLeft);
	}
}
