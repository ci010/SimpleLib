package test.realism;

import api.simplelib.common.ModHandler;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.world.BlockEvent;

/**
 * @author ci010
 */
@ModHandler
public class Handler
{
	public void onBreakBlock(BlockEvent.BreakEvent event)
	{
		if (event.state.getBlock().getBlockHardness(event.world, event.pos) > 1)
		{
			event.getPlayer().attackEntityFrom(new DamageSource("hurt"), 1);
		}
	}
}
