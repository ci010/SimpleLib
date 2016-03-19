package test.api.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import test.api.stack.ComponentStack;

/**
 * @author ci010
 */
public interface IEntityInteract extends ComponentItem
{

	/**
	 * Current implementations of this method in child classes do not use the entry argument beside ev. They just raise
	 * the damage on the stack.
	 *
	 * @param target   The Entity being hit
	 * @param attacker the attacking entity
	 */
	boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker);//TODO chech this

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
