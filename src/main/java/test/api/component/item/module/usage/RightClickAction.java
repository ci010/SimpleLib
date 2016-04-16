package test.api.component.item.module.usage;

import api.simplelib.ContextBlockInteract;
import com.google.common.base.Optional;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import test.api.component.entity.livingbase.StatePlayer;
import test.api.world.World;
import test.api.component.item.StateItem;

/**
 * @author ci010
 */
public interface RightClickAction
{
	/**
	 * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
	 */
	StateItem onItemRightClick(StateItem itemStackIn, World worldIn, StatePlayer playerIn);

	Optional<? extends BlockHandler> getBlockHandler();

	Optional<? extends EntityHandler> getEntityHandler();

	interface EntityHandler
	{
		/**
		 * Returns true if the item can be used on the given entity, e.g. shears on sheep.
		 */
		boolean onInteract(ItemStack stack, EntityPlayer playerIn, EntityLivingBase target);
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
		boolean onItemUseFirst(StateItem stack, StatePlayer player, ContextBlockInteract info);

		/**
		 * after {@link #onItemUseFirst(ItemStack, EntityPlayer, ContextBlockInteract)}
		 */
		boolean onItemUse(StateItem stack, StatePlayer playerIn, ContextBlockInteract info);

		/**
		 * Called when the player finishes using this Item (E.g. finishes eating.). Not called when the player stops using
		 * the Item before the action is complete.
		 */
		StateItem onItemUseFinish(StateItem stack, World worldIn, StatePlayer playerIn);

		/**
		 * Called each tick while using an item.
		 *
		 * @param stack  The Item being used
		 * @param player The Player using the item
		 * @param count  The amount of time in tick the item has been used for continuously
		 */
		void onUsingTick(StateItem stack, StatePlayer player, int count);

		/**
		 * Called when the player stops using an Item (stops holding the right mouse button).
		 */
		void onPlayerStoppedUsing(StateItem stack, World worldIn, StatePlayer playerIn, int timeLeft);
	}
}
