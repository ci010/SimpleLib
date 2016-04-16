package test.api.component.block;

import com.google.common.base.Optional;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.util.IStringSerializable;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import test.api.component.GameComponent;
import test.api.component.entity.StateEntity;
import test.api.component.entity.livingbase.StatePlayer;
import test.api.component.item.StateItem;
import test.api.world.World;

/**
 * @author ci010
 */
public interface ComponentBlock extends GameComponent<ComponentBlock.Builder, ContextBlock>
{
	CreativeTabs getCreativeTabs();

	Property getProperty();

	EnumWorldBlockLayer getBlockLayer();
//	interface Ability
//	{
//		boolean canReplace(World worldIn, BlockPos pos, EnumFacing side, ItemStack stack);
//
//		boolean canDropFromExplosion(Explosion explosionIn);
//
//		boolean canSilkHarvest(World world, BlockPos pos, IBlockState state, EntityPlayer player);
//
//		boolean canCreatureSpawn(IBlockAccess world, BlockPos pos, net.minecraft.entity.EntityLiving.SpawnPlacementType
//				type);
//
//		boolean canSustainLeaves(IBlockAccess world, BlockPos pos);
//
//		boolean canConnectRedstone(IBlockAccess world, BlockPos pos, EnumFacing side);
//	}

	interface Behavior
	{
		void onBlockBreak(World worldIn, BlockPos pos, StateBlock state, Optional<StateEntity> breaker);

		void onBlockPlace(World world, BlockPos pos, EnumFacing side, StateItem item, Optional<StatePlayer> placer);
	}

	interface TBehavior
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

	interface Builder extends GameComponent.Builder
	{
		IProperty<Integer> newInteger(String name, int defaultV, int min, int max);

		IProperty<Boolean> newBoolean(String name, boolean defaultV);

		<T extends Enum<T> & IStringSerializable> IProperty<T> newEnum(String name, Class<T> clz, T defulatV);

		IProperty<EnumFacing> newDirection(String name, EnumFacing defaultV);
	}

}
