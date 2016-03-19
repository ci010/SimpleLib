package test.api.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

/**
 * @author ci010
 */
public interface SneakSensitive extends ComponentItem
{
	/**
	 * Should this item, when held, allow sneak-clicks to pass through to the underlying block?
	 * <p/>
	 * Should sneaking player with this itemstack induce the onBlockActivated?
	 *
	 * @param world  The world
	 * @param pos    Block position in world
	 * @param player The Player that is wielding the item
	 * @return
	 */
	boolean doesSneakBypassUse(World world, BlockPos pos, EntityPlayer player);
}
