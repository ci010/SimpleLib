package test.api.component.item.module.hit;

import com.google.common.base.Optional;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

/**
 * @author ci010
 */
public interface LeftClickAction
{
	/**
	 * Called when a entity tries to play the 'swing' animation.
	 *
	 * @param entityLiving The entity swinging the item.
	 * @param stack        The Item stack
	 * @return True to cancel any further processing by EntityLiving
	 */
	boolean onEntitySwing(EntityLivingBase entityLiving, ItemStack stack);

	Optional<BlockHandler> getBlockHandler();

	Optional<EntityHandler> getEntityHandler();

	interface BlockHandler
	{
		/**
		 * Called before a block is broken.  Return true to prevent default block harvesting.
		 * <p/>
		 * Note: In SMP, this is called on both client and server sides!
		 *
		 * @param itemstack The current ItemStack
		 * @param pos       Block's position in world
		 * @param player    The Player that is wielding the item
		 * @return True to prevent harvesting, false to continue as normal
		 */
		boolean onBlockStartBreak(ItemStack itemstack, BlockPos pos, EntityPlayer player);

		/**
		 * Called when a Block is destroyed using this Item. Return true to trigger the "Use Item" statistic.
		 */
		boolean onBlockDestroyed(ItemStack stack, World worldIn, Block blockIn, BlockPos pos, EntityLivingBase playerIn);
	}

	interface EntityHandler
	{
		/**
		 * Called when the player Left Clicks (attacks) an entity.
		 * Processed before damage is done, if return value is true further processing is canceled
		 * and the entity is not attacked.
		 *
		 * @param stack  The Item being used
		 * @param player The player that is attacking
		 * @param entity The entity being attacked
		 * @return True to cancel the rest of the interaction.
		 */
		boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity);
	}
}
