package net.simplelib.world;

import net.minecraft.world.World;
import net.simplelib.common.nbt.ITagSerial;

/**
 * @author ci010
 */
public interface IExtendedWorldProperties extends ITagSerial
{
	void load(World world);

	void unLoad(World world);
}
