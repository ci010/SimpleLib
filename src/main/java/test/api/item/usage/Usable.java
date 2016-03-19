package test.api.item.usage;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

/**
 * @author ci010
 */
public abstract class Usable implements RightClickAction
{
	@Override
	public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn)
	{
		playerIn.setItemInUse(itemStackIn, getInfo().getMaxItemUseDuration(itemStackIn));
		return itemStackIn;
	}

	public abstract Info getInfo();

	public interface Info
	{
		EnumAction getItemUseAction(ItemStack stack);

		int getMaxItemUseDuration(ItemStack stack);
	}
}
