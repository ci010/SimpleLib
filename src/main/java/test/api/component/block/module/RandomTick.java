package test.api.component.block.module;

import test.api.world.World;


import java.util.Random;

/**
 * @author ci010
 */
public interface RandomTick
{
//	int tickRate(World world);

	void onRandomTick(World world, Random random);
}
