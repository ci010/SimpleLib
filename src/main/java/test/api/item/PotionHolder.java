package test.api.item;

import net.minecraft.item.ItemStack;

/**
 * @author ci010
 */
public interface PotionHolder extends ComponentItem
{
	String getPotionEffect(ItemStack stack);// TODO: 2016/1/24 abstract
}
