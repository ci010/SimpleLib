package test.api.item;

import api.simplelib.common.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import test.api.MinecraftComponent;
import test.api.item.hit.LeftClickAction;
import test.api.item.usage.RightClickAction;

import java.util.List;

/**
 * @author ci010
 */
public interface ComponentItem extends MinecraftComponent
{
	Behavior getBehavior();

	Info getBasicInfo();

	RightClickAction getRightClick();

	LeftClickAction getLeftClick();

	/**
	 * addInformation
	 *
	 * @return
	 */
	@Nullable
	List<String> getCustomInfo(ItemStack stack);

	interface Behavior
	{
		void onCreated(ItemStack stack, World worldIn, EntityPlayer playerIn);

		/**
		 * Called each tick as long the item is on a player inventory. Uses by maps to check if is on a player hand and
		 * update it's contents.
		 */
		void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected);
	}

	interface Info
	{
		/**
		 * Metadata-sensitive version of getStrVsBlock
		 *
		 * @param itemstack The Item Stack
		 * @param state     The block state
		 * @return The damage strength
		 */
		float getDigSpeed(ItemStack itemstack, IBlockState state);

		/**
		 * @param block     The block trying to harvest
		 * @param itemStack The itemstack used to harvest the block
		 * @return true if can harvest the block
		 */
		boolean canHarvestBlock(Block block, ItemStack itemStack);

		/**
		 * Queries the harvest level of this item stack for the specifred tool class,
		 * Returns -1 if this tool is not of the specified type
		 *
		 * @param stack     This item stack instance
		 * @param toolClass Tool Class
		 * @return Harvest level, or -1 if not the specified tool type.
		 */
		int getHarvestLevel(ItemStack stack, String toolClass);
	}

}
