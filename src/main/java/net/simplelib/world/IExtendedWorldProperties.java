package net.simplelib.world;

import net.minecraft.world.World;
import api.simplelib.utils.ITagSerializable;

/**
 * @author ci010
 */
public interface IExtendedWorldProperties extends ITagSerializable
{
	void load(World world);

	void unload(World world);
}
