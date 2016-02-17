package api.simplelib.sitting;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;

/**
 * @author ci010
 */
public interface Sitable
{
	Block sitableBlock();

	boolean shouldSit(EntityPlayer player, BlockPos block);

	float offsetVertical();

	float offsetHorizontal();
}
