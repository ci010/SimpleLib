package net.ci010.minecrafthelper.test;

import net.minecraft.item.ItemStack;

/**
 * @author ci010
 */
public class VarItemHolder extends Process.Var<ItemStack>
{
	private int id;
	public VarItemHolder(int id)
	{
		this. id = id;
	}
}
