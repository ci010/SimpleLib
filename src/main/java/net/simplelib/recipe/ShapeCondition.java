package net.simplelib.recipe;

import com.google.common.base.Predicate;
import net.minecraft.item.ItemStack;

import javax.annotation.Nullable;

/**
 * @author ci010
 */
public class ShapeCondition<T extends ItemStacks> implements Predicate<T>
{

	@Override
	public boolean apply(@Nullable T input)
	{
		for(ItemStack material : input.materials)
		{

		}
		return false;
	}
}
