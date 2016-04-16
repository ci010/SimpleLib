package api.simplelib.interactive.base.wrapper;

import api.simplelib.interactive.BlockInteractive;
import api.simplelib.interactive.Interactive;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

/**
 * This interface indicates that the
 * {@link #( EntityPlayer , BlockPos )} will be called
 * when {@link net.minecraft.block.Block#onBlockActivated(World, BlockPos, IBlockState, EntityPlayer, EnumFacing, float, float, float)}
 */
public interface BlockBaseWrapper //extends Interactive.Base<BlockInteractive>
{
	/**
	 * @return The block will activated the {@link #interactWith(EntityPlayer, BlockPos)}.
	 */
}
