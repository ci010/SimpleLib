package test.api.block;

import net.minecraft.world.World;

import java.util.Random;

/**
 * @author ci010
 */
public interface IRandomTick
{
	void onRandomTick(World world, Random random);
}
