package api.simplelib.world;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

/**
 * @author ci010
 */
public interface ChunkData extends ICapabilitySerializable<NBTTagCompound>
{
	NBTTagCompound getCustomData();
}
