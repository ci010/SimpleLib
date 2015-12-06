package net.ci010.minecrafthelper.data;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

/**
 * @author ci010
 */
public class VarItemHolder extends Var<ItemStack>
{
	private String name;

	public VarItemHolder(String name)
	{
		this.name = name;
	}

	public String getName()
	{
		return name;
	}

	@Override
	public void setData(ItemStack stack)
	{
		super.setData(stack);
	}
}
