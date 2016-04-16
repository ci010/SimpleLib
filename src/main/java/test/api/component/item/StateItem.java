package test.api.component.item;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import test.api.component.State;

/**
 * @author ci010
 */
public interface StateItem extends State, ICapabilitySerializable<NBTTagCompound>
{
	@Override
	ComponentItem getType();

	boolean isStackable();

	int getStackSize();

	void setStackSize(int size);

	int getMeta();
}
