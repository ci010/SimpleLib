package net.simplelib.interactive.process;

import api.simplelib.VarBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import api.simplelib.utils.ITagSerializable;

/**
 * @author ci010
 */
public class VarItemHolder extends VarBase<ItemStack> implements ITagSerializable
{
	@Override
	public void set(ItemStack stack)
	{
		super.set(stack);
	}

	@Override
	public void readFromNBT(NBTTagCompound tag)
	{
		this.get().readFromNBT(tag);
	}

	@Override
	public void writeToNBT(NBTTagCompound tag)
	{
		this.get().writeToNBT(tag);
	}
}
