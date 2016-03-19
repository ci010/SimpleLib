package test.api.stack;

import net.minecraft.item.ItemStack;
import test.api.stack.ComponentStack;

/**
 * @author ci010
 */
public enum StackFactory
{
	INSTANCE;

	public ComponentStack of(final ItemStack stack)
	{
		return new ComponentStack()
		{
			@Override
			public int getMaxMeta()
			{
				return stack.getMaxDamage();
			}

			@Override
			public int getCurrentMeta()
			{
				return stack.getItemDamage();
			}
		};
	}
}
