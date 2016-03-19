package test.api.block;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import test.api.HitInfo;

/**
 * @author ci010
 */
public interface BlockHandler
{
	boolean onBlockActivated(IBlockState state, EntityPlayer playerIn, HitInfo info);

	void onBlockClicked(World worldIn, BlockPos pos, EntityPlayer playerIn);

	boolean onBlockEventReceived(World worldIn, BlockPos pos, IBlockState state, int eventID, int eventParam);

	void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn);

	void onBlockExploded(World world, BlockPos pos, Explosion explosion);
}
