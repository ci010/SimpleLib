package test.api.block;

import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

/**
 * @author ci010
 */
public interface HandlerRainTime
{
	/**
	 * fillWithRain
	 *
	 * @param worldIn
	 * @param pos
	 */
	void onRainDropOn(World worldIn, BlockPos pos);
}
