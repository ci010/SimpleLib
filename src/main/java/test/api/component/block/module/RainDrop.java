package test.api.component.block.module;

import net.minecraft.util.BlockPos;
import test.api.world.World;


/**
 * @author ci010
 */
public interface RainDrop
{
	/**
	 * fillWithRain
	 *
	 * @param worldIn
	 * @param pos
	 */
	void onRainDropOn(World worldIn, BlockPos pos);
}
