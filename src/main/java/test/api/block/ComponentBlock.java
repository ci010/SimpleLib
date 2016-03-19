package test.api.block;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import test.api.MinecraftComponent;

import java.util.List;

/**
 * @author ci010
 */
public interface ComponentBlock extends MinecraftComponent
{
	Material getMaterial();

	List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune);

	Ability getAbility();

	Behavior getBehavior();

	interface Ability
	{
		boolean canReplace(World worldIn, BlockPos pos, EnumFacing side, ItemStack stack);

		boolean canDropFromExplosion(Explosion explosionIn);

		boolean canSilkHarvest(World world, BlockPos pos, IBlockState state, EntityPlayer player);

		boolean canCreatureSpawn(IBlockAccess world, BlockPos pos, net.minecraft.entity.EntityLiving.SpawnPlacementType
				type);

		boolean canSustainLeaves(IBlockAccess world, BlockPos pos);

		boolean canConnectRedstone(IBlockAccess world, BlockPos pos, EnumFacing side);
	}

	interface Behavior
	{
		/**
		 * onBlockPlaced
		 *
		 * @return
		 */
		IBlockState onBlockPlacedPre(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int
				meta, EntityLivingBase placer);

		/**
		 * onBlockPlacedBy
		 *
		 * @param world
		 * @param pos
		 * @param state
		 * @param placer
		 * @param stack
		 */
		void onBlockPlacedPost(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack);
	}
}
