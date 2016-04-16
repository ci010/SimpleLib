package test.api.component.item.module;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

/**
 * @author ci010
 */
public interface ItemHandler
{
	/**
	 * Called when a player drops the item into the world,
	 * returning false from this will prevent the item from
	 * being removed from the players inventory and spawning
	 * in the world
	 *
	 * @param player The player that dropped the item
	 * @param item   The item stack, before the item is removed.
	 */
	boolean onDroppedByPlayer(ItemStack item, EntityPlayer player);

	/**
	 * onEntitySwing
	 * 
	 * Called when a entity tries to play the 'swing' animation.
	 *
	 * @param entityLiving The entity swinging the item.
	 * @param stack        The Item stack
	 * @return True to cancel any further processing by EntityLiving
	 */
	boolean shouldSwing(EntityLivingBase entityLiving, ItemStack stack);
}
