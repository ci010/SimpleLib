package net.simplelib.entity;

import api.simplelib.VarSync;
import api.simplelib.utils.GenericUtil;
import api.simplelib.seril.ITagSerializable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

/**
 * Internal usage
 *
 * @author ci010
 */
public class CapabilityDelegate implements ICapabilitySerializable<NBTTagCompound>
{
	@CapabilityInject(CapabilityDelegate.class)
	public static final Capability<CapabilityDelegate> DELEGATE = null;

	private VarSync[] tagSerializables;
	private Capability[] types;

	public CapabilityDelegate()
	{

	}

	public CapabilityDelegate(VarSync[] tagSerializables, Capability<?>[] type)
	{
		this.tagSerializables = tagSerializables;
		this.types = type;
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing)
	{
		for (Capability type : types)
			if (type == capability)
				return true;
		return false;
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing)
	{
		for (int i = 0; i < types.length; i++)
			if (types[i] == capability)
				return GenericUtil.cast(tagSerializables[i]);
		return null;
	}

	@Override
	public NBTTagCompound serializeNBT()
	{
		NBTTagCompound compound = new NBTTagCompound();
		for (ITagSerializable serializable : tagSerializables)
			serializable.writeToNBT(compound);
		return compound;
	}

	@Override
	public void deserializeNBT(NBTTagCompound nbt)
	{
		for (ITagSerializable serializable : tagSerializables)
			serializable.readFromNBT(nbt);
	}
}
