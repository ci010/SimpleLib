package test.api.block;

import net.minecraft.world.World;

import java.util.Random;

/**
 * @author ci010
 */
public interface ITick
{
	int tickRate(World worldIn);

	void tick(World world, Random random);
}
