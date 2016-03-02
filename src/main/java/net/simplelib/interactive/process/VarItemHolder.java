package net.simplelib.interactive.process;

import net.minecraft.item.ItemStack;
import api.simplelib.VarBase;
import net.minecraft.nbt.NBTTagCompound;
import net.simplelib.common.nbt.ITagSerial;

/**
 * @author ci010
 */
public class VarItemHolder extends VarBase<ItemStack> implements ITagSerial
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
