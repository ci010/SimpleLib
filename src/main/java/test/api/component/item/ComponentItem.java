package test.api.component.item;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import test.api.component.Context;
import test.api.component.GameComponent;
import test.api.component.item.capabilities.CustomInfo;
import test.api.component.item.capabilities.Damageable;
import test.api.component.item.capabilities.Nameable;
import test.api.component.item.module.hit.LeftClickAction;
import test.api.component.item.module.usage.RightClickAction;

/**
 * @author ci010
 */
public interface ComponentItem extends GameComponent<GameComponent.Builder, Context>
{
	@CapabilityInject(Nameable.class)
	Capability<Nameable> nameble = null;

	@CapabilityInject(CustomInfo.class)
	Capability<CustomInfo> customInfo = null;

	@CapabilityInject(Damageable.class)
	Capability<Damageable> damageable = null;

	CreativeTabs getCreativeTabs();

	int getMaxMeta();

	RightClickAction getRightClick();

	LeftClickAction getLeftClick();

	interface ItemBehavior
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
	}

}
